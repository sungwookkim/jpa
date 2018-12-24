package common.util.valueString.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import common.util.valueString.inter.ValueString;

public class JSON implements ValueString {

	public static <T> String valueString(T t) {
		String rtnValueString = "";

		try {
			rtnValueString = new ObjectMapper().writeValueAsString(t);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		return rtnValueString;		
	}

}
