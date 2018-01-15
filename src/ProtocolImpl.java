public class ProtocolImpl implements Protocol {  
   
    byte[] buffer = new byte[1024];  
    int tail = 0;  
    int state=0;
    float bufor[]= {2,2};
    public void onReceive(byte b) {  
        // simple protocol: each message ends with new line  
        if (b=='\n') {  
            onMessage();  
        } else {  
            buffer[tail] = b;  
            tail++;  
        }  
    }  
   
    public void onStreamClosed() {  
        onMessage();  
    }  
      
    /* 
     * When message is recognized onMessage is invoked  
     */  
    private void onMessage() {  
        if (tail!=0) {  
            // constructing message  
        	//Window Wobject = new Window();
        	
        	String message = getMessage(buffer, tail);  
        
        		int wartosc=-1;
        		if(message.contains("READ")) {
        		try {
        			
        			wartosc =Integer.parseInt(Window.extractDigits(message));
        			bufor[state]=wartosc;     			
        		
        		
        			state++;
				} catch (NullPointerException exc) {

				} catch (NumberFormatException exc) {

				}}
        		
        	
        	if(state==2) {
				float temp;
				temp = (bufor[0]+(bufor[1]*256))/16;
				Window.setT2Text(Float.toString(temp)); ///Mateusz
				
				state=0;
			}
        	if(!message.contains("READ"))
            System.out.println("RECEIVED MESSAGE: " + message);  
              
            // this logic should be placed in some kind of   
            // message interpreter class not here  
        
            tail = 0;  
        }  
      
    }  
      
    // helper methods   
    public byte[] getMessage(String message) {  
        return (message+"\n").getBytes();  
    }  
      
    public String getMessage(byte[] buffer, int len) {  
        return new String(buffer, 0, tail);  
    }  
}  