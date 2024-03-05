package com.availity.service;
import java.io.*;
import java.util.*;

class Enrollee {
    String userId;
    String firstName;
    String lastName;
    int version;
    String insuranceCompany;

    public Enrollee(String userId, String firstName, String lastName, int version, String insuranceCompany) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.version = version;
        this.insuranceCompany = insuranceCompany;
    }

    @Override
    public String toString() {
        return userId + "," + firstName + "," + lastName + "," + version;
    }
}

public class EnrollmentProcessor {
    public static void main(String[] args) {
        Map<String, List<Enrollee>> enrolleesByCompany = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("enrollment_data.csv"))) {
            br.readLine(); // Skipping header
            String line;
            while ((line = br.readLine()) != null) {
                String[] enrollData = line.split(",");
                Enrollee enrollee = new Enrollee(enrollData[0], enrollData[1], enrollData[2], Integer.parseInt(enrollData[3]), enrollData[4]);
                enrolleesByCompany
                        .computeIfAbsent(enrollee.insuranceCompany, k -> new ArrayList<>()).add(enrollee);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println(enrolleesByCompany);

        enrolleesByCompany.forEach((company, enrollees) -> {
            enrollees.sort(Comparator.comparing((Enrollee e) -> e.lastName)
                    .thenComparing(e -> e.firstName));
            Map<String, Enrollee> uniqueEnrollees = new HashMap<>();
            enrollees.forEach(e -> uniqueEnrollees.merge(e.userId, e, (existing, replacement) ->
                    existing.version > replacement.version ? existing : replacement));

            try (PrintWriter writer = new PrintWriter(new FileWriter(company + "_enrollees.csv"))) {
                writer.println("User Id,First Name,Last Name,Version");
                uniqueEnrollees.values().forEach(writer::println);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
}
