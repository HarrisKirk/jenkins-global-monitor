import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.ParseException;

public class HudsonTimeComparator {

	Date jobDate 
	Date currentDate
	final static long MILLISECS_PER_MIN = 60 * 1000
	final static long MILLISECS_PER_HR =  MILLISECS_PER_MIN * 60
	final static long MILLISECS_PER_DAY =  MILLISECS_PER_HR * 24
	final static String GMT_OFFSET = "-0000"
	
	public static final String JAVA_DATE_FORMAT = "yyyy-MM-dd HH:mm:ssZ";
	
	HudsonTimeComparator( String jobTimeString ) {
		jobDate = createGmtDate( jobTimeString + GMT_OFFSET )
		currentDate = new Date()
	}
	HudsonTimeComparator( String jobTimeString, String mockCurrentDate ) {
		jobDate = 		createGmtDate( jobTimeString + GMT_OFFSET )
		currentDate = 	createGmtDate( mockCurrentDate + GMT_OFFSET)
	}
	
	Date createGmtDate( String dateTimeString ) {
		return new SimpleDateFormat( JAVA_DATE_FORMAT ).parse( replaceTandZ( dateTimeString ) ) 
	}

	static String replaceTandZ( String hudsonTime ) {
		return hudsonTime.replaceAll("T", " ").replaceAll("Z", "");
	}
	
	int getDifferenceDays() {
		return ( currentDate.getTime() - jobDate.getTime() ) / MILLISECS_PER_DAY
	}
	
	int getDifferenceHrs() {
		return ( currentDate.getTime() - jobDate.getTime() ) / MILLISECS_PER_HR
	}
	
	int getDifferenceMins() {
		return ( currentDate.getTime() - jobDate.getTime() ) / MILLISECS_PER_MIN
	}

	String getDisplayTime() {
		String displayString
		if ( getDifferenceMins() <=120 ) {
			displayString = sprintf("%4d mins ago", getDifferenceMins() )
		} else {
			if ( getDifferenceHrs() <=48 ) {
				displayString = sprintf("%4d hrs ago", getDifferenceHrs() )
			} else {
				displayString = sprintf("%4d days ago", getDifferenceDays() )
			}
		}
		return displayString
	}

}

