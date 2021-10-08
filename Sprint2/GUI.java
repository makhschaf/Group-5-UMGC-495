import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;// need this for connection
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import java.util.Arrays; 




public class GUI {
	static API api = new API();
	static Boolean  convert = true;
	static String conversionRate, req_result;
	static String[] currencyTypes = {"USD", "GBP", "EUR","AOA","AFN","ALL",
            "AMD","ANG","AOA","ARS","AUD","AWG","AZN","BAM","BBD","BDT","BGN",
            "BHD","BIF","BMD","BND","BOB","BRL","BSD","BTN","BWP","BYN","BZD",
            "CAD","CDF","CHF","CLP","CNY","COP","CRC","CUC","CUP","CVE","CZK",
            "DJF","DKK","DOP","DZD","EGP","ERN","ETB","EUR","FJD","FKP","FOK",
            "GBP","GEL","GGP","GHS","GIP","GMD","GNF","GTQ","GYD","HKD","HNL",
            "HRK","HTG","HUF","IDR","ILS","IMP","INR","IQD","IRR","ISK","JMD",
            "JOD","JPY","KES","KGS","KHR","KID","KMF","KRW","KWD","KYD","KZT",
            "LAK","LBP","LKR","LRD","LSL","LYD","MAD","MDL","MGA","MKD","MMK",
            "MNT","MOP","MRU","MUR","MVR","MWK","MXN","MYR","MZN","NAD","NGN",
            "NIO","NOK","NPR","NZD","OMR","PAB","PEN","PGK","PHP","PKR","PLN",
            "PYG","QAR","RON","RSD","RUB","RWF","SAR","SBD","SCR","SDG","SEK",
            "SGD","SHP","SLL","SOS","SRD","SSP","STN","SYP","SZL","THB","TJS",
            "TMT","TND","TOP","TRY","TTD","TVD","TWD","TZS","UAH","UGX","USD",
            "UYU","UZS","VES","VND","VUV","WST","XAF","XCD","XDR","XOF","XPF",
            "YER","ZAR","ZMW"};
	
	
	static String inAmount, selectedInputCurrency, selectedOutputCurrency;
	static JTextField currencyAmountField;
	static JTextField conversionTotalField;
	static JComboBox inputCurrency; 
	static JComboBox outputCurrency; 

  
 
   
	public static void main(String[] args) {
		 
			Arrays.sort(currencyTypes);
			inputCurrency = new JComboBox(currencyTypes); 
			outputCurrency = new JComboBox(currencyTypes);
			currencyAmountField = new JTextField(12);
			conversionTotalField = new JTextField(12); 
			JFrame frame = new JFrame("Currency Converter");
			JLabel title = new JLabel("Currency Converter", JLabel.CENTER);
			JLabel inputAmount = new JLabel("Enter amount: ");
			JLabel outputAmount = new JLabel("The total is: ");
			JLabel convRate = new JLabel("The conversion rate is " + conversionRate);
	       
			JPanel panel = new JPanel();
			JLabel inputCurrencyLabel = new JLabel("Input Currency"); 
			JLabel outputCurrencyLabel = new JLabel("Output Currency"); 

			JButton calculateButton = new JButton("Calculate"); 
	       
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			frame.setSize(700, 700); 
			frame.setLayout(new GridLayout(0, 4));
	     

	       
	      
	       
			panel.add(title);
			panel.add(inputAmount);
			panel.add(currencyAmountField); 
			panel.add(inputCurrencyLabel); 
	       	panel.add(inputCurrency); 
	       	panel.add(outputCurrencyLabel); 
	       	panel.add(outputCurrency); 
	       	panel.add(calculateButton); 
	       	panel.add(convRate);
	       	panel.add(outputAmount);
	       	panel.add(conversionTotalField); 
	       	frame.add(panel);
	              frame.setVisible(true); 
	              
	calculateButton.addActionListener(new ActionListener(){
                               @Override
	            	   public void actionPerformed(ActionEvent ae){
	            	      inAmount = currencyAmountField.getText();
	            	      double inputAmountDouble = 0.0;
	            	      Boolean amountEntered = true;
	            	      Boolean amountDouble = true;
	            	      Boolean differentCurrency = true;
	            	      Boolean notNegative = true;
	            	      if (inAmount.equals("")) {
	            	    	  JOptionPane.showMessageDialog(null, "Please enter the amount.");
	            	    	  amountEntered  = false;
	            	      }
	            	      else {
	            	    	  amountEntered = true;
	            	    	  try {	            	    	  
		            	    	  inputAmountDouble = Double.parseDouble(inAmount);
		            	    	  amountDouble = true;
		            	      }
		            	      
		            	      catch (NumberFormatException e) {
		            	    	    JOptionPane.showMessageDialog(null, "Input String cannot be parsed to float.");
		            	    	    amountDouble = false;
		            	      }
	            	      }
	            	      
	            	      if (inputAmountDouble<0) {
	            	    	  JOptionPane.showMessageDialog(null, "Please enter a positive number.");
	            	    	  notNegative  = false;
	            	      }
	            	      
	            	      else {
	            	    	  notNegative = true;
	            	      }
	            	      
	            	      String selectedInputCurrency= inputCurrency.getSelectedItem().toString();
		    	          String selectedOutputCurrency= outputCurrency.getSelectedItem().toString();
		    	          if (selectedInputCurrency == selectedOutputCurrency) {
		    	        	  JOptionPane.showMessageDialog(null, "The input and output currency cannot be the same.");
		    	        	  differentCurrency = false;
		    	          }
		    	          else {
		    	        	  differentCurrency = true;
		    	          }
	            	      
	            	      if(amountDouble && amountEntered && differentCurrency && notNegative) {
	            	    	  api.setURL(api.getURL() + selectedInputCurrency + "/");
	            	    	  api.setURL(api.getURL() + selectedOutputCurrency + "/");
		    	          try {
							setUpAPI();
		    	          	} 
		    	          catch (Exception e) {	
		    	        	  JOptionPane.showMessageDialog(null, "There is an issue in getting the exchange rates from the internet. Please check your wifi / internet connection for connectivity and restart the application.");
	            	    	    
		    	          	}
		    	          
		    	          double conversionRateDouble = Double.parseDouble(conversionRate);
		    	          double total = inputAmountDouble * (conversionRateDouble);
		    	          String totalText = String.valueOf(total);
		    	          conversionTotalField.setText(totalText);
		    	          api.clearURL();
		    	          
	            	      }
	            	   }
	            	});
	     
	}
	
	 public static void setUpAPI() throws Exception {

       
		URL url = new URL(api.getURL());
		HttpURLConnection request = (HttpURLConnection) url.openConnection();
		request.addRequestProperty("User-Agent", 
				"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.0)");
		request.connect();

		// Convert to JSON
		
		JsonElement root = JsonParser.parseReader(new InputStreamReader((InputStream) request.getContent()));
		JsonObject jsonobj = root.getAsJsonObject();

		// Accessing object
		req_result = jsonobj.get("result").getAsString();
		//baseCurrency = jsonobj.get("base_code").getAsString();
		//outputCurrency= jsonobj.get("target_code").getAsString();
		conversionRate=jsonobj.get("conversion_rate").getAsString();
		      
             
     }// end of setUpAPI method
	 
	 
	 

	 
}//end of GUI Class
