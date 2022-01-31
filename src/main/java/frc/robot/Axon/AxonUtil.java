package frc.robot.Axon;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.ObjectMapper;

import edu.wpi.first.networktables.NetworkTable;

public class AxonUtil {
	public static final String DETECTIONS_KEY = "detections";
	public static final String CORAL_KEY = "coral";
	public static final String RESOLUTION_KEY = "resolution";
	public static Detection[] getAxonDetections(NetworkTable table){
		String json = table.getEntry(DETECTIONS_KEY).getString("");
		return parseAxonDetections(json);
	}

	public static AxonResult getAxonResult(NetworkTable table){
		AxonResult result = new AxonResult();
		result.setDetections(parseAxonDetections(table.getEntry(DETECTIONS_KEY).getString("")));
		result.setCoral(Boolean.valueOf(table.getEntry(CORAL_KEY).getString(Boolean.FALSE.toString())));
		String resolution = table.getEntry(RESOLUTION_KEY).toString();
		if (resolution != null){
		String [] resolutionList = resolution.split(",");
			result.setResolutionWidth(Integer.parseInt(resolutionList[0]));
			result.setResolutioHeight(Integer.parseInt(resolutionList[1]));
		}
		return result;
	}
	public static Detection[] parseAxonDetections(String json){
		Detection[] results = null;
		try {
			ObjectMapper mapper = new ObjectMapper();
			results = mapper.readValue(json, Detection[].class);
		}catch(Exception e){

		}
		return results;
	}

}
