package Database;

import com.example.proj.Faq;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Faq_Handler implements DatabaseConfig {

    // Method to insert an FAQ
    public boolean insertFaq(Faq faq) {
        String query = "INSERT INTO faq (patientID, doctorID, Question, Answer) VALUES (?, ?, ?, ?)";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, faq.getPatientID());
            stmt.setInt(2, faq.getDoctorID());
            stmt.setString(3, faq.getQuestion());
            stmt.setString(4, faq.getAnswer());
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve all FAQs
    public List<Faq> getAllFaqs() {
        List<Faq> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Faq faq = new Faq();
                faq.setPatientID(rs.getInt("patientID"));
                faq.setDoctorID(rs.getInt("doctorID"));
                faq.setQuestion(rs.getString("Question"));
                faq.setAnswer(rs.getString("Answer"));
                faqs.add(faq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faqs;
    }

    // Method to update an FAQ's answer
    public boolean updateFaqAnswer(int patientID, int doctorID, String newAnswer) {
        String query = "UPDATE faq SET Answer = ? WHERE patientID = ? AND doctorID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, newAnswer);
            stmt.setInt(2, patientID);
            stmt.setInt(3, doctorID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // Method to retrieve FAQs by doctor ID
    public List<Faq> getFaqsByDoctorId(int doctorId) {
        List<Faq> faqs = new ArrayList<>();
        String query = "SELECT * FROM faq WHERE doctorID = ?";

        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, doctorId);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Faq faq = new Faq();
                faq.setPatientID(rs.getInt("patientID"));
                faq.setDoctorID(rs.getInt("doctorID"));
                faq.setQuestion(rs.getString("Question"));
                faq.setAnswer(rs.getString("Answer"));
                faqs.add(faq);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return faqs;
    }

    // Method to delete an FAQ
    public boolean deleteFaq(int patientID, int doctorID) {
        String query = "DELETE FROM faq WHERE patientID = ? AND doctorID = ?";
        try (Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, patientID);
            stmt.setInt(2, doctorID);
            return stmt.executeUpdate() > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
