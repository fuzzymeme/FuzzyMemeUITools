package com.fuzzymeme.uitools.testrigs;

import com.fuzzymeme.uitools.logger.Logger;
import com.fuzzymeme.uitools.logger.LoggerKey;

public class LoggerTestRig {
	
	private Logger log;
	
	private enum LogKey implements LoggerKey {
		X, Y, COUNT, DEBUG, INFO
	};
	
	public void callback(Logger callbackLog) {
		this.log = callbackLog;
		log.addInitialKeys(LogKey.X);
	}
	
	public static void main(String[] args) {

		LoggerTestRig rig = new LoggerTestRig();
		rig.run();
	}
	
	private void run() {
		Logger.setRig(this);
		new Thread() {			
			public void run(){
				Logger.show();
			}
		}.start();
        
		try {
			Thread.sleep(2000);
		} catch (InterruptedException ie) {
		}

        double x = 0, y = 0;

        final long startNanoTime = System.nanoTime();
        int loopCount = 0;
        while(true) {
            log.log(LogKey.X, "x: " + x);
            
            if(loopCount > 2) {
            	log.log(LogKey.Y, "y: " + y);
            }
            if(loopCount > 4) {
            	log.log(LogKey.DEBUG, "Debug: " + y);
            }
            if(loopCount > 5) {
            	log.log(LogKey.COUNT, "Count: " + loopCount);
            }
            
            double t = (System.nanoTime() - startNanoTime) / 1000000000.0; 

            if(loopCount > 6) {
            	log.log(LogKey.INFO, "t: " + t);
            }


            x = 256 + 128 * Math.cos(t);
            y = 256 + 128 * Math.sin(t);

            try {
				Thread.sleep(400);
			} catch (InterruptedException ie) {
			}
            
            loopCount++;
        }
    }    
}
