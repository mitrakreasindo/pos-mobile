-- Bernaridho. 2017-07-29.
ALTER TABLE "Step05".Breaks ADD CONSTRAINT PK_Breaks PRIMARY KEY (id);

ALTER TABLE "Step05".Product_Categories ADD CONSTRAINT PK_Product_Categories PRIMARY KEY (id);

ALTER TABLE "Step05".Closed_Cashes ADD CONSTRAINT PK_Closed_Cashes PRIMARY KEY (money);

ALTER TABLE "Step05".CSV_Imports ADD CONSTRAINT PK_CSV_Imports PRIMARY KEY (id);

ALTER TABLE "Step05".Consumers ADD CONSTRAINT PK_Consumers PRIMARY KEY (id);

ALTER TABLE "Step05".Drawer_Openings ADD CONSTRAINT PK_Drawer_Openings PRIMARY KEY (id);

ALTER TABLE "Step05".Leaves ADD CONSTRAINT PK_Leaves PRIMARY KEY (id);

ALTER TABLE "Step05".Removed_items ADD CONSTRAINT PK_Removed_items PRIMARY KEY (id);

ALTER TABLE "Step05".Locations ADD CONSTRAINT PK_Locations PRIMARY KEY (id);

ALTER TABLE "Step05".Purchase_orders ADD CONSTRAINT PK_Purchase_orders PRIMARY KEY (id);

ALTER TABLE "Step05".Payments ADD CONSTRAINT PK_Payments PRIMARY KEY (id);

ALTER TABLE "Step05".Persons ADD CONSTRAINT PK_Persons PRIMARY KEY (id);

ALTER TABLE "Step05".Merchants ADD CONSTRAINT PK_Merchants PRIMARY KEY (code);

--ALTER TABLE "Step05".Places ADD CONSTRAINT PK_Places PRIMARY KEY (id);

ALTER TABLE "Step05".Products ADD CONSTRAINT PK_Products PRIMARY KEY (id);

ALTER TABLE "Step05".Product_coms ADD CONSTRAINT PK_Product_coms PRIMARY KEY (id, Product_id, Product2_id); -- previously (id, Product, Product2)

ALTER TABLE "Step05".Product_kits ADD CONSTRAINT PK_Product_kits PRIMARY KEY (id);

ALTER TABLE "Step05".Promotions ADD CONSTRAINT PK_Promotions PRIMARY KEY (id);

ALTER TABLE "Step05".Receipts ADD CONSTRAINT PK_Receipts PRIMARY KEY (id);

ALTER TABLE "Step05".Roles ADD CONSTRAINT PK_Roles PRIMARY KEY (id);

ALTER TABLE "Step05".Shift_breaks ADD CONSTRAINT PK_Shift_breaks PRIMARY KEY (id);

ALTER TABLE "Step05".Shifts ADD CONSTRAINT PK_Shifts PRIMARY KEY (id);

ALTER TABLE "Step05".Stock_Changes ADD CONSTRAINT PK_Stock_Changes PRIMARY KEY (id);

ALTER TABLE "Step05".Stock_Diaries ADD CONSTRAINT PK_Stock_Diaries PRIMARY KEY (id);

ALTER TABLE "Step05".Stock_Levels ADD CONSTRAINT PK_Stock_Levels PRIMARY KEY (id);

ALTER TABLE "Step05".Tax_Categories ADD CONSTRAINT PK_Tax_Categories PRIMARY KEY (id);

ALTER TABLE "Step05".Taxes ADD CONSTRAINT PK_Taxes PRIMARY KEY (id);

ALTER TABLE "Step05".Tax_Items ADD CONSTRAINT PK_Tax_Items PRIMARY KEY (id);

ALTER TABLE "Step05".Third_Parties ADD CONSTRAINT PK_Third_Parties PRIMARY KEY (id);

ALTER TABLE "Step05".Sales ADD CONSTRAINT PK_Sales PRIMARY KEY (id);

ALTER TABLE "Step05".Sales_items ADD CONSTRAINT PK_Sales_items PRIMARY KEY (Sales_id, line); -- previously (Ticket, line)

ALTER TABLE "Step05".Vouchers ADD CONSTRAINT PK_Vouchers PRIMARY KEY (Name); -- previously voucher

-- Comment ==================================================================================

ALTER TABLE "Step05".Product_Categories
 ADD CONSTRAINT FK_Product_Categories 
  FOREIGN KEY (Parent_Id) -- previously ParentId
  REFERENCES "Step05".Product_Categories (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION; -- changed

ALTER TABLE "Step05".Leaves
    ADD CONSTRAINT FK_Leaves FOREIGN KEY (Person_Id) REFERENCES "Step05".Persons (id); -- changed

ALTER TABLE "Step05".Payments
    ADD CONSTRAINT FK_payments FOREIGN KEY (Receipt_id) REFERENCES "Step05".Receipts (id); -- changed

ALTER TABLE "Step05".Persons
    ADD CONSTRAINT FK_Persons FOREIGN KEY (role_id) REFERENCES "Step05".Roles (id); -- changed

ALTER TABLE "Step05".Products -- changed, renamed, reordered
    ADD CONSTRAINT FK1_Products FOREIGN KEY (Category_id) REFERENCES "Step05".Product_Categories (id);

ALTER TABLE "Step05".Products -- changed, renamed, reordered
 ADD CONSTRAINT FK2_Products 
  FOREIGN KEY (PackProduct_id) 
  REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Products -- changed, renamed
    ADD CONSTRAINT FK3_Products FOREIGN KEY (Promotion_Id) REFERENCES "Step05".Promotions (id);

ALTER TABLE "Step05".Products -- changed
    ADD CONSTRAINT FK4_Products FOREIGN KEY (TaxCategory_id) REFERENCES "Step05".Tax_Categories (id);

ALTER TABLE "Step05".Product_coms -- changed
    ADD CONSTRAINT FK1_Product_coms FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Product_coms -- changed
    ADD CONSTRAINT FK2_Product_coms FOREIGN KEY (Product2_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Product_kits -- changed
    ADD CONSTRAINT FK1_Product_kits FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Product_kits -- unchanged
    ADD CONSTRAINT FK2_Product_kits FOREIGN KEY (Product_kit) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Receipts -- changed references
    ADD CONSTRAINT FK_Receipts FOREIGN KEY (id) REFERENCES "Step05".Sales (id);

ALTER TABLE "Step05".Shift_breaks -- changed
    ADD CONSTRAINT FK1_Shift_breaks FOREIGN KEY (Break_Id) REFERENCES "Step05".Breaks (id);

ALTER TABLE "Step05".Shift_breaks -- changed
    ADD CONSTRAINT FK2_Shift_breaks FOREIGN KEY (Shift_Id) REFERENCES "Step05".Shifts (id);

ALTER TABLE "Step05".Stock_Changes -- changed
    ADD CONSTRAINT FK1_Stock_Changes FOREIGN KEY (Location_id) REFERENCES "Step05".Locations (id);

ALTER TABLE "Step05".Stock_Changes -- changed
    ADD CONSTRAINT FK2_Stock_Changes FOREIGN KEY (Product_Id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Stock_Currents -- changed, renamed
    ADD CONSTRAINT FK1_Stock_Currents FOREIGN KEY (Location_id) REFERENCES "Step05".Locations (id);

ALTER TABLE "Step05".Stock_Currents -- changed
    ADD CONSTRAINT FK2_Stock_Currents FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Stock_Diaries -- changed, renamed
    ADD CONSTRAINT FK1_Stock_Diaries FOREIGN KEY (Location_id) REFERENCES "Step05".Locations (id);

ALTER TABLE "Step05".Stock_Diaries -- changed
    ADD CONSTRAINT FK2_Stock_Diaries FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Stock_Levels -- changed
    ADD CONSTRAINT FK1_Stock_Levels FOREIGN KEY (Location_id) REFERENCES "Step05".Locations (id);

ALTER TABLE "Step05".Stock_Levels -- changed
    ADD CONSTRAINT FK2_Stock_Levels FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Taxes -- changed
    ADD CONSTRAINT FK1_Taxes FOREIGN KEY (Category_id) REFERENCES "Step05".Tax_Categories (id);

ALTER TABLE "Step05".Taxes -- changed, renamed
    ADD CONSTRAINT FK2_Taxes FOREIGN KEY (Parent_Id) REFERENCES "Step05".Taxes (id);

ALTER TABLE "Step05".Tax_Items -- changed
    ADD CONSTRAINT FK1_Tax_Items FOREIGN KEY (Receipt_id) REFERENCES "Step05".Receipts (id);

ALTER TABLE "Step05".Tax_Items -- changed
    ADD CONSTRAINT FK2_Tax_Items FOREIGN KEY (Tax_Id) REFERENCES "Step05".Taxes (id);

ALTER TABLE "Step05".Sales_items -- changed, renamed
    ADD CONSTRAINT FK1_Sales_items FOREIGN KEY (Product_id) REFERENCES "Step05".Products (id);

ALTER TABLE "Step05".Sales_items -- changed, renamed
    ADD CONSTRAINT FK2_Sales_items FOREIGN KEY (Tax_Id) REFERENCES "Step05".Taxes (id);

ALTER TABLE "Step05".Sales_items -- changed, renamed
    ADD CONSTRAINT FK3_Sales_items FOREIGN KEY (Sales_id) REFERENCES "Step05".Sales (id);

ALTER TABLE "Step05".Sales -- changed
    ADD CONSTRAINT FK1_Sales FOREIGN KEY (Consumer_id) REFERENCES "Step05".Consumers (id);

ALTER TABLE "Step05".Sales -- changed
    ADD CONSTRAINT FK2_Sales FOREIGN KEY (Person_id) REFERENCES "Step05".Persons (id);

--ALTER TABLE "Step05".Sales
--    ADD CONSTRAINT FK3_Sales FOREIGN KEY  (id) REFERENCES "Step05".Receipts (id);