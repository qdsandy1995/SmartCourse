import com.cloud.smartcourse.backend;
import com.google.api.client.googleapis.auth.clientlogin.ClientLogin;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.Properties;
import java.util.logging.Logger;

public classs Course_Servlet extends HttpServlet{
  static Logger Log = Logger.getLogger("com.cloud.smartcourse.backend");
  
  public void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws IOException {
    FirebaseOptions options = new FirebaseOptions.Builder()
            .setServiceAccount(getServletContext().getResourceAsStream("/WEB-INF/google-service.json"))
            .setDatabaseUrl("https://smartcourse-e4806.firebaseio.com/")
            .build();

        try {
            FirebaseApp.getInstance();
        }
        catch (Exception error){
            Log.info("doesn't exist...");
        }

        try {
            FirebaseApp.initializeApp(options);
        }
        catch(Exception error){
            Log.info("already exists...");
        }
  DatabaseReference ref = FirebaeDatabase.getInstance().getReference("Course_data");
  ref.addListenerForSingleValueEvent(new ValueEventListener(){
    
  @Override
  public void onDataChange(DataSnapshot dataSnapshot)
  {
    Object document = dataSnapshot.getValue();
    Iterator<DataSnapshot> children = dataSnapshot.getChildren().iterator();
    while(children.hasNext())
     {
       DataSnapshot childSnapshot = (DataSnapshot) children.next();
       todoText = todoText + " * " + childSnapshot.getValue().toString() + "\n";
     }
  }
  });
  }
}
