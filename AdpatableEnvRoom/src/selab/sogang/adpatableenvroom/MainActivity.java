package selab.sogang.adpatableenvroom;

import selab.sogagn.adpatableenvroom.R;
import selab.sogang.bundle.BundleController;
import selab.sogang.persondata.RequestController;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;


public class MainActivity extends ActionBarActivity {
	private BundleController bundleController;
	private RequestController requestController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        
        //bundleController와 RequestController 초기화
        
        
        
    }
    private void initRequest()
    {
    	this.bundleController = new BundleController(this, "data", this.getResources(), null);
        this.requestController = new RequestController(bundleController);
        
        requestController.setPreference(23,26,45,50,38,42);
        
    }
    
    



}
