package testIni;
import org.ini4j.Ini;

import java.io.FileReader;

public class TestIni {
	public static final String FILENAME = "config/page.ini";

    public static void main(String[] args) throws Exception
    {
        String filename = (args.length > 0) ? args[0] : FILENAME;
        Ini ini = new Ini(new FileReader(filename));

        for (String key : ini.keySet())
        {
        	System.out.println("*** section name: " + key);
        	System.out.println("--- 1. pageId  = " + ini.get(key).get("pageId"));
        	System.out.println("--- 2. pageUrl  = " + ini.get(key).get("pageUrl"));
        }
    }
}
