package br.edu.ifrn.firebase_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.util.UUID;

public class MainActivity extends AppCompatActivity {
    //private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    //Atributo para autenticação no Firebase
    //private FirebaseAuth usuario = FirebaseAuth.getInstance();

    private ImageView imageFoto;
    private Button buttonUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonUpload    = findViewById(R.id.buttonUpload);
        imageFoto       = findViewById(R.id.imageFoto);

        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Configura para imagem ser salva em memória
                imageFoto.setDrawingCacheEnabled(true);
                imageFoto.buildDrawingCache();

                //Recupera bitmap da imagem (imagem a ser carregada)
                Bitmap bitmap = imageFoto.getDrawingCache();

                //Comprime bitmap para um formato png/jpeg
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 75, baos);

                /**
                 * Converte o baos para pixel brutos em uma matriz de bytes
                 * (dados da imagem)
                 */
                byte[] dadosImagem = baos.toByteArray();

                //Define os nós para o storage
                StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                //Será criada uma pasta com nome imagens dentro do Firebase Storage
                StorageReference imagens = storageReference.child("imagens");
                StorageReference imagemRef = imagens.child("celular.jpeg");

                //Para fazer download da imagem
                Glide.with(MainActivity.this)
                        .load(imagemRef)
                        .into(imageFoto);

                //String nomeArquivo = UUID.randomUUID().toString();
                //StorageReference imagemRef = imagens.child(nomeArquivo + ".jpeg");
                //Retorna objeto que irá controlar o upload

                //UploadTask uploadTask = imagemRef.putBytes(dadosImagem);
                //uploadTask.addOnFailureListener(MainActivity.this, new OnFailureListener() {
                    //@Override
                    //public void onFailure(Exception e) {
                        //Toast.makeText(MainActivity.this,
                                //"Upload da imagem falhou: " + e.getMessage().toString(),
                                //Toast.LENGTH_LONG).show();
                    //}
                //}).addOnSuccessListener(MainActivity.this, new OnSuccessListener<UploadTask.TaskSnapshot>() {
                   // @Override
                    //public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        //imagemRef.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                           // @Override
                           // public void onComplete(Task<Uri> task) {
                               // Uri url = task.getResult();
                               // Toast.makeText(MainActivity.this,
                                        //"Upload realizado com sucesso: ",
                                        //Toast.LENGTH_LONG).show();
                            //}
                        //});
                   // }
                //});
            }
        });

                /**
                 * //Para deletar um arquivo
                 *                 imagemRef.delete().addOnFailureListener(MainActivity.this, new OnFailureListener() {
                 *                     @Override
                 *                     public void onFailure(Exception e) {
                 *                         Toast.makeText(MainActivity.this,
                 *                                 "Erro ao deletar " ,
                 *                                 Toast.LENGTH_LONG).show();
                 *                     }
                 *                 }).addOnSuccessListener(MainActivity.this, new OnSuccessListener<Void>() {
                 *                     @Override
                 *                     public void onSuccess(Void aVoid) {
                 *                         Toast.makeText(MainActivity.this,
                 *                                 "Arquivo deletado com sucesso! " ,
                 *                                 Toast.LENGTH_LONG).show();
                 *                     }
                 *                 });
                 */
            //}
        //});
    }
}