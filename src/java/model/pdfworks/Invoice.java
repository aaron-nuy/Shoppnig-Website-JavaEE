package model.pdfworks;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.dataTypes.Product;
import model.dataTypes.User;
import model.dbManager.Manager;

public class Invoice {

	static public byte[] render(ArrayList<Product> productList,User user) {
		
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		Document document = new Document();
		
		try {	
			PdfWriter.getInstance(document, buffer);
			document.open();


			
			Paragraph companyName = new Paragraph(new Phrase(10f,"PFC 2022 Inc.",
		               FontFactory.getFont(FontFactory.COURIER_BOLD, 30)));
			companyName.setAlignment(Element.ALIGN_CENTER);
			document.add(companyName);


			
			Paragraph header = new Paragraph();
			Paragraph lastName = new Paragraph("Last Name: "+ user.getLastName());
			Paragraph firstName = new Paragraph("First Name: "+ user.getFirstName());
			Paragraph email = new Paragraph("Email: "+ user.geteMail());
			Paragraph empty = new Paragraph(" ");
			
			Paragraph order = new Paragraph("Order Number: 63D23NZEOX/03");
			order.setFont(FontFactory.getFont(FontFactory.TIMES_ITALIC,13f,BaseColor.GRAY));
			order.setAlignment(Element.ALIGN_RIGHT);
			email.add(order);
			
			header.add(lastName);
			header.add(firstName);
			header.add(email);
			header.add(empty);
			
			document.add(header);
			
			PdfPTable table = new PdfPTable(3);
			
			
	        PdfPCell cell = new PdfPCell(new Phrase("Item"));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        
	        table.addCell(cell);

	        cell = new PdfPCell(new Phrase("Supplier"));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);

	       
	        cell = new PdfPCell(new Phrase("Price"));
	        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
	        table.addCell(cell);
	        table.setHeaderRows(1);

	        PdfPCell innerCell = new PdfPCell();
	        innerCell.setPaddingLeft(10);
	        innerCell.setPaddingTop(5);
	        innerCell.setPaddingBottom(5);
	        double totalPrice = 0;
	        
	        for(Product product : productList) {
	        	innerCell.setPhrase(new Phrase(product.getName()));
		        table.addCell(innerCell);
		        
		        
		        User supplier = Manager.getUser(product.getSupplier_id());
	        	innerCell.setPhrase(new Phrase(supplier.getFirstName() + " " + supplier.getLastName()));
		        table.addCell(innerCell);
		        
	        	innerCell.setPhrase(new Phrase(String.valueOf(product.getPrice()) + " DA"));
		        table.addCell(innerCell);
		        
		        totalPrice += product.getPrice();
	        }

	        document.add(table);
	        
	        
	        Paragraph price = new Paragraph("Total: " + String.valueOf(totalPrice) + " DA");
	        price.setAlignment(Paragraph.ALIGN_RIGHT);
	        price.setSpacingAfter(20);
	        document.add(price);
	        
	        
			Paragraph footer = new Paragraph(new Phrase(5f,"Our Website: www.usdb.dz",
		               FontFactory.getFont(FontFactory.HELVETICA_OBLIQUE, 15)));
			footer.setAlignment(Element.ALIGN_CENTER);
			footer.setAlignment(Element.ALIGN_BOTTOM);
			footer.setPaddingTop(document.bottom());
			document.add(footer);
			

			


			
		} catch (Exception e) {
			e.printStackTrace();
		}
		finally {
			document.close();
		}


		return buffer.toByteArray();
	}

	
}
