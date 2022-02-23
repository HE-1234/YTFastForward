package com.codepath.apps.restclienttemplate

import Playcom.codeparth.apps.restclienttemplate.MainActivity
import android.annotation.SuppressLint
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import com.codepath.apps.restclienttemplate.models.SampleModel
import com.codepath.apps.restclienttemplate.models.SampleModelDao
import com.codepath.oauth.OAuthLoginActionBarActivity
import com.google.android.gms.auth.GoogleAuthException
import com.google.android.gms.auth.GoogleAuthUtil
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.common.api.Scope
import com.google.android.gms.tasks.Task
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube


class LoginActivity : OAuthLoginActionBarActivity<RestClient>() {

    var sampleModelDao: SampleModelDao? = null
    lateinit var mGoogleSignInClient: GoogleSignInClient
    val RC_SIGN_IN = 9001
    var accessToken: String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val sampleModel = SampleModel()
        sampleModel.name = "CodePath"
        sampleModelDao = (applicationContext as RestApplication).myDatabase?.sampleModelDao()
        AsyncTask.execute { sampleModelDao?.insertModel(sampleModel) }

        val gso: GoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .requestScopes(Scope("https://www.googleapis.com/auth/youtube"))
            .requestServerAuthCode("689135212469-1spl0e79b927an0fffis49hga46vqfdh.apps.googleusercontent.com")
            .build()

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso)
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
            
            Log.i(TAG, "token found async: " + accessToken)
            val tokenResponse = TokenResponse()
            tokenResponse.accessToken = accessToken
            val credential: Credential = createCredentialWithAccessTokenOnly(tokenResponse)
            val youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), credential).build()

            val request: YouTube.Videos.List = youtube.videos()
                .list("snippet,contentDetails,statistics")
            val response = request.setMyRating("like").execute()
            Log.i("GettingChannels", response.toString())
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            // Signed in successfully, show authenticated UI.
            val task: AsyncTask<Void?, Void?, String?> = @SuppressLint("StaticFieldLeak")
            object : AsyncTask<Void?, Void?, String?>() {
                @SuppressLint("StaticFieldLeak")
                override fun onPostExecute(token: String?) {
                    Log.i(TAG, "Access token retrieved:$token")
                    if (token != null) {
                        accessToken = token
                    }
                }

                override fun doInBackground(vararg params: Void?): String? {
                    var token: String? = null
                    try {
                        token = getTokenWithAccount(account)
                    } catch (authEx: GoogleAuthException) {
                        // The call is not ever expected to succeed
                        // assuming you have already verified that
                        // Google Play services is installed.
                        Log.e(TAG, authEx.toString())
                    }
                    return token
                }
            }
            task.execute()
            Thread.sleep(12000)

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.w(TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    fun getTokenWithAccount(account: GoogleSignInAccount): String? {
        var token: String? = null
        try {
            token = GoogleAuthUtil.getToken(this, account.account!!, "oauth2:https://www.googleapis.com/auth/youtube")
        } catch (e: Exception) {
            Log.w(TAG, "token:failed code=" + e.message)
        }
        return token
    }

    private fun createCredentialWithAccessTokenOnly(tokenResponse: TokenResponse): Credential {
        return Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
            tokenResponse
        )
    }

    // Inflate the menu; this adds items to the action bar if it is present.
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.login, menu)
        return true
    }

    // OAuth authenticated successfully, launch primary authenticated activity
    // i.e Display application "homepage"
    override fun onLoginSuccess() {
        Log.i("LoginActivity", "Logged in successfully!")

        val i = Intent(this, MainActivity::class.java)
        startActivity(i)
    }

    // OAuth authentication flow failed, handle the error
    // i.e Display an error dialog or toast
    override fun onLoginFailure(e: Exception) {
        e.printStackTrace()
    }

    // Click handler method for the button used to start OAuth flow
    // Uses the client to initiate OAuth authorization
    // This should be tied to a button used to login
    fun loginToRest(view: View?) {
        signIn()
//        client.connect()
    }

    companion object {
        const val TAG = "LoginActivity"
    }
}