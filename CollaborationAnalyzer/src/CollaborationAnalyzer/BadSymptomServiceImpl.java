package CollaborationAnalyzer;

import CollaborationAnalyzer.service.BadSymptomService;

import java.util.ArrayList;
import java.util.List;

import org.osgi.framework.BundleContext;

public class BadSymptomServiceImpl implements BadSymptomService {
	
	private BundleContext bundleContext = null;
	public BadSymptomServiceImpl(BundleContext bundleContext) {
		this.bundleContext = bundleContext;
	}

	private ArrayList diffList;

	private void initRuleSet() {
		diffList = new ArrayList<String>();
		for(int i=0;i<3;i++)
			diffList.add("satisfied");
	}


	@Override
	public ArrayList reason(List currentBM, List designModel) {
		// TODO Auto-generated method stub
		//designmodel 0,1(low high temp) 2,3 (low high humi) 4,5 (low,high bright)
		//designmodel is Preference Data
		//currentBM is Room Data 0:temp 1:humi 2: bright
		initRuleSet();
		
		//temperature
		if((double)currentBM.get(0) < (double)designModel.get(0))
		{
			diffList.set(0, "Low " + ((double)designModel.get(0) - (double)currentBM.get(0)));
		}
		else if((double)currentBM.get(0) > (double)designModel.get(0))
		{
			diffList.set(0, "High " + ((double)currentBM.get(1) - (double)designModel.get(0)));
		}
		//humidity
		if((double)currentBM.get(1) < (double)designModel.get(2))
		{
			diffList.set(0, "Low " + ((double)designModel.get(1) - (double)currentBM.get(2)));
		}
		else if((double)currentBM.get(1) > (double)designModel.get(3))
		{
			diffList.set(0, "High " + ((double)currentBM.get(3) - (double)designModel.get(1)));
		}
		//brightness
		if((double)currentBM.get(2) < (double)designModel.get(4))
		{
			diffList.set(0, "Low " + ((double)designModel.get(2) - (double)currentBM.get(4)));
		}
		else if((double)currentBM.get(2) > (double)designModel.get(5))
		{
			diffList.set(0, "High " + ((double)currentBM.get(5) - (double)designModel.get(2)));
		}
		return diffList;
	}

	@Override
	public void compareBM(List currentBM, List designModel) {
		// TODO Auto-generated method stub
		
	}

}
