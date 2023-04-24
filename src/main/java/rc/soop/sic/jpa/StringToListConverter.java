/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package rc.soop.sic.jpa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

/**
 *
 * @author Administrator
 */
@Converter
public class StringToListConverter implements AttributeConverter<List<String>, String> {

    @Override
    public String convertToDatabaseColumn(List<String> list) {
        if (list == null) {
            return "";
        }
        return String.join(";", list);
    }

    @Override
    public List<String> convertToEntityAttribute(String joined) {
        if (joined == null) {
            return new ArrayList<>();
        }
        return new ArrayList<>(Arrays.asList(joined.split(";")));
    }
}
