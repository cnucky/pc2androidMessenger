package com.sadman.pc2an;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedWriter;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.URL;


public class MainActivity extends AppCompatActivity {

    private Socket clientSocket;
    Handler updateConversationHandler;
    Button btn_main;
    EditText editText_main;
    private  final int port= 1670;
    private  String server_ip ="192.168.1.5";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn_main=findViewById(R.id.btn_main);
        editText_main =findViewById(R.id.edit_main);


        btn_main.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    String message=  editText_main.getText().toString();
                    Log.e("message", message );
                    new sendMessageClass().execute(message);


                } catch (NullPointerException nullexception)
                {
                    nullexception.printStackTrace();
                    editText_main.setError("try again");
                    Toast.makeText(getApplicationContext(), "Server is not Available", Toast.LENGTH_SHORT).show();

                }
                catch (Exception e)
                {
                    Toast.makeText(getApplicationContext(), e.toString(), Toast.LENGTH_SHORT).show();
                    Log.e("error", e.toString() );

                }
                finally {
                    editText_main.setText("");

                }



            }
        });
    }


    class sendMessageClass extends AsyncTask<String, Void, Void> {


        protected Void doInBackground(String... message) {
            try {
                clientSocket = new Socket(server_ip, port);
                // jURL url = new URL(urls[0]);
                //DataOutputStream dataOutputStream = new DataOutputStream(clientSocket.getOutputStream());
                //dataOutputStream.write(32);

                PrintWriter printWriter = new PrintWriter( new BufferedWriter( new OutputStreamWriter(clientSocket.getOutputStream())), true);
                printWriter.println(message);
                printWriter.flush();
                printWriter.close();
                clientSocket.close();
                Toast.makeText(getApplicationContext(), "Message sent successfully", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Log.e( "doInBackground: ", e.toString());

                return null;
            } finally {

            }
            return null;
        }


    }
}
