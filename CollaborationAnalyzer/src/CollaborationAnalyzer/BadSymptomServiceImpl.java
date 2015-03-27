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
		if((Double)currentBM.get(0) < (Double)designModel.get(0))
		{
			diffList.set(0, "Low " + ((Double)designModel.get(0) - (Double)currentBM.get(0)));
		}
		else if((Double)currentBM.get(0) > (Double)designModel.get(1))
		{
			diffList.set(0, "High " + ((Double)designModel.get(1) - (Double)currentBM.get(0)));
		}
		//humidity
		if((Double)currentBM.get(1) < (Double)designModel.get(2))
		{
			diffList.set(1, "Low " + ((Double)designModel.get(2) - (Double)currentBM.get(1)));
		}
		else if((Double)currentBM.get(1) > (Double)designModel.get(3))
		{
			diffList.set(1, "High " + ((Double)designModel.get(3) - (Double)currentBM.get(1)));
		}
		//brightness
		if((Double)currentBM.get(2) < (Double)designModel.get(4))
		{
			diffList.set(2, "Low " + ((Double)designModel.get(4) - (Double)currentBM.get(2)));
		}
		else if((Double)currentBM.get(2) > (Double)designModel.get(5))
		{
			diffList.set(2, "High " + ((Double)designModel.get(5) - (Double)currentBM.get(2)));
		}
		return diffList;
	}

	@Override
	public void compareBM(List currentBM, List designModel) {
		// TODO Auto-generated method stub
		
	}

}
