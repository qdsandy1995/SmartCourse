import com.example.smartcourse.myapplication.backend;
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
  static Logger Log = Logger.getLogger("com.example.smartcourse.myapplication.backend");
  DatabaseReference ref = FirebaeDatabase.getInstance().getReference("Course_data");
  ref.addListenerForSingleValueEvent(new ValueEventListern)
    
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
}
