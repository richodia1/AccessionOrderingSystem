/**
 * 
 */
package org.iita.accessions2.servlet;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
 
import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
public class HitCounterAction extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Connection dbConnection = null;

	public HitCounterAction() {
        super();
        this.dbConnection = DataAccessObject.getConnection();
    }       
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateHitCounter();
        getHitCounterImage(request, response);
    }      
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        updateHitCounter();
        getHitCounterImage(request, response);
    }
 
    private void updateHitCounter() {
        try {
        	dbConnection = DataAccessObject.getConnection(); 
            
            // 
            // Update the hits counter table by incrementing the 
            // counter every time a user hits our page.
            //
            String sql = "UPDATE Hits SET counter = counter + 1";
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            stmt.executeUpdate();            
        } catch (SQLException e) {
        	System.out.println("ERROR CLOSSING CONNECTION ON UPDATE");
            e.printStackTrace();
        } finally {
            //closeConnection(this.dbConnection);
        }
    }
    
    private void getHitCounterImage(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String hits = "0";
        try {
        	dbConnection = DataAccessObject.getConnection(); 
            
            // 
            // Get the current hits counter from database.
            //
            String sql = "SELECT counter FROM Hits";
            PreparedStatement stmt = dbConnection.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                hits = rs.getString("counter");
            }
        } catch (SQLException e) {
        	System.out.println("ERROR CLOSSING CONNECTION ON SELECT");
            e.printStackTrace();
        } finally {
            //closeConnection(this.dbConnection);
        }
        
        // 
        // Create an image of our counter to be sent to the browser.
        //
        System.out.println("Hits ON SELECT: " + hits);
        BufferedImage buffer = new BufferedImage(50, 20, BufferedImage.TYPE_INT_RGB);
        Graphics2D g = buffer.createGraphics();
        g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
            RenderingHints.VALUE_ANTIALIAS_ON);
        g.setFont(new Font("Monospaced", Font.PLAIN, 14));
        g.setColor(Color.WHITE);
        g.fillRect(0, 0, 50, 20);
        g.setColor(Color.BLACK);
        g.drawString(hits, 0, 20);
        
        response.setContentType("image/png");
        OutputStream os = response.getOutputStream();
        ImageIO.write(buffer, "png", os);
        os.close();
    }
}