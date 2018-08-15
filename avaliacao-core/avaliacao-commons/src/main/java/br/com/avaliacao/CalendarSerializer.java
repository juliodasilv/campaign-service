package br.com.avaliacao;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

public class CalendarSerializer extends JsonSerializer<Calendar> {

	private SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");

	@Override
	public void serialize(Calendar calendar, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
			throws IOException {
		String dateAsString = formatter.format(calendar.getTime());
		jsonGenerator.writeString(dateAsString);
	}
}