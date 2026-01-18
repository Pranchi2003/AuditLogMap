package org.example;

import com.fasterxml.jackson.databind.ObjectMapper;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) throws Exception{
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        System.out.println("Hello and welcome!");

        //  Create object
        AuditLogMultiMap<String, String> map = new AuditLogMultiMap<>();

        // ADD values
        System.out.println("---- ADD VALUES ----");
        map.addValue("USER1", "LOGIN");
        map.addValue("USER1", "LOGOUT");
        map.addValue("USER2", "LOGIN");


        //Duplicate value (will NOT be added)
        map.addValue("USER1", "LOGIN");

        //  REMOVE value
        System.out.println("---- REMOVE VALUE ----");
        map.removeValue("USER1", "LOGIN");

        System.out.println("Total Keys: " + map.totalKeys());

        //VALUES COUNT PER KEY
        System.out.println("Values per Key: " + map.valuesCountPerKey());

        //KEYS WITH NO VALUES
        System.out.println("Keys with no values: " + map.keysWithNoValues());

        //AUDIT HISTORY
        System.out.println("Audit USER1:");
        map.auditHistory("USER1").forEach(System.out::println);

        //REMOVE KEY
        System.out.println("---- REMOVE KEY ----");
        map.removeKey("USER2");

        // EXPORT AS JSON
        System.out.println("---- JSON OUTPUT ----");
        ObjectMapper mapper = new ObjectMapper();
        String json = mapper.writerWithDefaultPrettyPrinter()
                .writeValueAsString(map.toJsonCompatibleMap());

        System.out.println(json);
    }
}