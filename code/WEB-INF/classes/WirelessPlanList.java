import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/WirelessPlanList")

public class WirelessPlanList extends HttpServlet {

	/* Trending Page Displays all the Tablets and their Information in Game Speed */

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter pw = response.getWriter();

	/* Checks the Tablets type whether it is microsft or apple or samsung */

		String name = null;
		String CategoryName = request.getParameter("maker");
		HashMap<String, WirelessPlan> hm = new HashMap<String, WirelessPlan>();

		if (CategoryName == null)	
		{
			hm.putAll(SaxParserDataStore.wirelessPlanHashmap);
			name = "";
		} 
		else 
		{
			if(CategoryName.equals("Basic")) 
			{	
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessPlanHashmap.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Basic"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name ="Basic";
			} 
			else if (CategoryName.equals("Premium"))
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessPlanHashmap.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Premium"))
				  {
					 hm.put(entry.getValue().getId(),entry.getValue());
				  }
				}
				name = "Premium";
			} 
			else if (CategoryName.equals("Ultimate")) 
			{
				for(Map.Entry<String,WirelessPlan> entry : SaxParserDataStore.wirelessPlanHashmap.entrySet())
				{
				  if(entry.getValue().getRetailer().equals("Ultimate"))
				 {
					 hm.put(entry.getValue().getId(),entry.getValue());
				 }
				}	
				name = "Ultimate";
			}
	    }

		/* Header, Left Navigation Bar are Printed.

		All the tablets and tablet information are dispalyed in the Content Section

		and then Footer is Printed*/

		Utilities utility = new Utilities(request, pw);
		utility.printHtml("Header.html");
		utility.printHtml("LeftNavigationBar.html");
		pw.print("<div id='content'><div class='post'><h2 class='title meta'>");
		pw.print("<a style='font-size: 24px;'>" + name + " WirelessPlans</a>");
		pw.print("</h2><div class='entry'><table id='bestseller'>");
		int i = 1;
		int size = hm.size();
		for (Map.Entry<String, WirelessPlan> entry : hm.entrySet()) {
			WirelessPlan wirelessPlan = entry.getValue();
			if (i % 3 == 1)
				pw.print("<tr>");
			pw.print("<td><div id='shop_item'>");
			pw.print("<h3>" + wirelessPlan.getName() + "</h3>");
			pw.print("<strong>" + wirelessPlan.getPrice() + "$</strong><ul>");
			pw.print("<li id='item'><img src='images/WirelessPlans/"
					+ wirelessPlan.getImage() + "' alt='' /></li>");
			pw.print("<li><form method='post' action='Cart'>" +
					"<input type='hidden' name='name' value='"+wirelessPlan.getName()+"'>"+
					"<input type='hidden' name='type' value='wirelessPlan'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+wirelessPlan.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
					"<input type='submit' class='btnbuy' value='Buy Now'></form></li>");
			pw.print("<li><form method='post' action='WriteReview'>"+"<input type='hidden' name='name' value='"+wirelessPlan.getName()+"'>"+
					"<input type='hidden' name='type' value='wirelessPlans'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+wirelessPlan.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='WriteReview' class='btnreview'></form></li>");
			pw.print("<li><form method='post' action='ViewReview'>"+"<input type='hidden' name='name' value='"+wirelessPlan.getName()+"'>"+
					"<input type='hidden' name='type' value='wirelessPlans'>"+
					"<input type='hidden' name='maker' value='"+CategoryName+"'>"+
					"<input type='hidden' name='price' value='"+wirelessPlan.getPrice()+"'>"+
					"<input type='hidden' name='access' value=''>"+
				    "<input type='submit' value='ViewReview' class='btnreview'></form></li>");
			pw.print("</ul></div></td>");
			if (i % 3 == 0 || i == size)
				pw.print("</tr>");
			i++;
		}
		pw.print("</table></div></div></div>");
		utility.printHtml("Footer.html");
	}
}
