package gov.samhsa.c2s.staffuiapi.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UmsDbID {
    private static Logger logger = LoggerFactory.getLogger(UmsDbID.class.getName());
    private static ThreadLocal<String> currentTenant = new ThreadLocal<>();
    private static ThreadLocal<String> currentVersion = new ThreadLocal<>();
    private static ThreadLocal<String> currentToken = new ThreadLocal<>();
    
    public static void setCurrentTenant(String tenant) {
        logger.debug("Setting tenant to " + tenant);
   System.out.println("UmsDb__Setting tenant to " + tenant);
        currentTenant.set(tenant);
    } 
    public static String getCurrentTenant() {
        return currentTenant.get();
    }
    
    public static void setCurrentVersion(String version) {
        logger.debug("Setting version to " + version);
        currentVersion.set(version);
    }
    public static String getCurrentVersion() {
        return currentVersion.get();
    }  
    
    public static void setCurrentToken(String token) {
        logger.debug("Setting version to " + token);
        currentToken.set(token);
    }
    public static String getCurrentToken() {
        return currentToken.get();
    } 
    
    public static void clear() {
        currentTenant.set(null);
        currentVersion.set(null);
        //currentToken.set(null);
    }
 
}