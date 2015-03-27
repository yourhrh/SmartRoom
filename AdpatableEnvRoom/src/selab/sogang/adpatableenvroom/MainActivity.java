package selab.sogang.adpatableenvroom;
import selab.sogagn.adpatableenvroom.R;
import selab.sogang.bundle.BundleController;
import selab.sogang.persondata.DrawService;
import selab.sogang.persondata.RequestController;
import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;


public class MainActivity extends Activity {
	private BundleController bundleController;
	private RequestController requestController;
	public static TextView preferenceView;
	public static TextView envView;
	public static Activity mainActivity;
	private Button adaptButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        adaptButton = (Button)findViewById(R.id.adapt);
        preferenceView = (TextView)findViewById(R.id.preference);
        envView = (TextView)findViewById(R.id.env);
        mainActivity = this;
        
        //bundleController와 RequestController 초기화
       initRequest();
        
        
    }
    private void initRequest()
    {
    	this.bundleController = new BundleController(this,getFilesDir().getAbsolutePath(), getResources(),null);
    	bundleController.getFelix().getBundleContext().registerService(DrawService.class, 
    			new DrawService(), null);
        this.requestController = new RequestController(bundleController);        
        requestController.setPreference(23,26,45,50,38,42);
        bundleController.installBundles();
        adaptButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				new AdapatAsync().execute(requestController);
				
			}
		});
//        
    }
    private class AdapatAsync extends AsyncTask<RequestController, Void , Void>{

		@Override
		protected Void doInBackground(RequestController... params) {
			// TODO Auto-generated method stub
			params[0].startAdaptation();
			return null;
		}
    	
    }

		
    
    



}
