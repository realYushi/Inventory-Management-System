Project Description:

This project appears to be an Inventory Management System implemented in Java. From a user's perspective, the system likely offers the following features:

Dashboard: Users can view a summary of their inventory and financial status. This includes:

Financial summary (total sales, costs, net profit, and gross margin percentage)
Inventory summary (including low stock alerts)
Product Management: Users can add, update, delete, and view products. Each product has details such as ID, name, category, quantity, unit, and price.

Category Management: Users can manage product categories, including creating, updating, deleting, and viewing categories. Each category is associated with a supplier.

Supplier Management: Users can manage suppliers, including adding, updating, deleting, and viewing supplier information.

Inventory Transactions: Users can record various types of inventory transactions:

Sales: Decrease product quantity and record revenue
Purchases: Increase product quantity and record costs
Spoilage: Record loss of inventory
Reporting: The system likely provides some form of reporting, given the financial summary calculations.

The user interface appears to be text-based, likely using a console or terminal interface (based on the use of 'lanterna' library in the pom.xml file).

The system stores data in JSON files (product.json, category.json, transaction.json, supplier.json), allowing for data persistence between sessions.

This Inventory Management System would be useful for small to medium-sized businesses looking to keep track of their inventory, sales, purchases, and overall financial health related to their stock.

For more detailed information about the user interface and specific functionalities, you might want to look at the APP.java file, which likely contains the main application logic and user interaction flow.

