-- Bernaridho. 2017-07-27.
ALTER TABLE "Step04".Breaks ADD CONSTRAINT PK_Breaks PRIMARY KEY (id);

ALTER TABLE "Step04".Product_Categories ADD CONSTRAINT PK_Product_Categories PRIMARY KEY (id);

ALTER TABLE "Step04".Closed_Cashes ADD CONSTRAINT PK_Closed_Cashes PRIMARY KEY (money);

ALTER TABLE "Step04".CSV_Imports ADD CONSTRAINT PK_CSV_Imports PRIMARY KEY (id);

ALTER TABLE "Step04".Consumers ADD CONSTRAINT PK_Consumers PRIMARY KEY (id);

ALTER TABLE "Step04".Drawer_Openings ADD CONSTRAINT PK_Drawer_Openings PRIMARY KEY (id);

ALTER TABLE "Step04".Leaves ADD CONSTRAINT PK_Leaves PRIMARY KEY (id);

ALTER TABLE "Step04".Removed_items ADD CONSTRAINT PK_Removed_items PRIMARY KEY (id);

ALTER TABLE "Step04".Locations ADD CONSTRAINT PK_Locations PRIMARY KEY (id);

ALTER TABLE "Step04".Purchase_orders ADD CONSTRAINT PK_Purchase_orders PRIMARY KEY (id);

ALTER TABLE "Step04".Payments ADD CONSTRAINT PK_Payments PRIMARY KEY (id);

ALTER TABLE "Step04".Persons ADD CONSTRAINT PK_Persons PRIMARY KEY (id);

ALTER TABLE "Step04".Merchants ADD CONSTRAINT PK_Merchants PRIMARY KEY (code);

ALTER TABLE "Step04".Places ADD CONSTRAINT PK_Places PRIMARY KEY (id);

ALTER TABLE "Step04".Products ADD CONSTRAINT PK_Products PRIMARY KEY (id);

ALTER TABLE "Step04".Product_coms ADD CONSTRAINT PK_Product_coms PRIMARY KEY (id, Product, Product2);

ALTER TABLE "Step04".Product_kits ADD CONSTRAINT PK_Product_kits PRIMARY KEY (id);

ALTER TABLE "Step04".Promotions ADD CONSTRAINT PK_Promotions PRIMARY KEY (id);

ALTER TABLE "Step04".Receipts ADD CONSTRAINT PK_Receipts PRIMARY KEY (id);

ALTER TABLE "Step04".Roles ADD CONSTRAINT PK_Roles PRIMARY KEY (id);

ALTER TABLE "Step04".Shift_breaks ADD CONSTRAINT PK_Shift_breaks PRIMARY KEY (id);

ALTER TABLE "Step04".Shifts ADD CONSTRAINT PK_Shifts PRIMARY KEY (id);

ALTER TABLE "Step04".Stock_Changes ADD CONSTRAINT PK_Stock_Changes PRIMARY KEY (id);

ALTER TABLE "Step04A".Stock_Currents ADD CONSTRAINT PK_Stock_Currents PRIMARY KEY (Location, Product); -- added

ALTER TABLE "Step04".Stock_Diaries ADD CONSTRAINT PK_Stock_Diaries PRIMARY KEY (id);

ALTER TABLE "Step04".Stock_Levels ADD CONSTRAINT PK_Stock_Levels PRIMARY KEY (id);

ALTER TABLE "Step04".Tax_Categories ADD CONSTRAINT PK_Tax_Categories PRIMARY KEY (id);

ALTER TABLE "Step04".Taxes ADD CONSTRAINT PK_Taxes PRIMARY KEY (id);

ALTER TABLE "Step04".Tax_Items ADD CONSTRAINT PK_Tax_Items PRIMARY KEY (id);

ALTER TABLE "Step04".Third_Parties ADD CONSTRAINT PK_Third_Parties PRIMARY KEY (id);

ALTER TABLE "Step04".Sales ADD CONSTRAINT PK_Sales PRIMARY KEY (id);

ALTER TABLE "Step04".Sales_items ADD CONSTRAINT PK_Sales_items PRIMARY KEY (Ticket, line);

ALTER TABLE "Step04".Vouchers ADD CONSTRAINT PK_Vouchers PRIMARY KEY (voucher);

-- Comment ==================================================================================

ALTER TABLE "Step04".Product_Categories
 ADD CONSTRAINT FK_Product_Categories 
  FOREIGN KEY (ParentId) 
  REFERENCES "Step04".Product_Categories (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE "Step04".Leaves
    ADD CONSTRAINT FK_Leaves FOREIGN KEY (PplId) REFERENCES "Step04".Persons (id);

ALTER TABLE "Step04".Payments
    ADD CONSTRAINT FK_payments FOREIGN KEY (Receipt) REFERENCES "Step04".Receipts (id);

ALTER TABLE "Step04".Persons
    ADD CONSTRAINT FK_Persons FOREIGN KEY (role) REFERENCES "Step04".Roles (id);

ALTER TABLE "Step04".Products
    ADD CONSTRAINT FK_Products FOREIGN KEY (PromotionId) REFERENCES "Step04".Promotions (id);

ALTER TABLE "Step04".Products
    ADD CONSTRAINT FK2_Products FOREIGN KEY (Category) REFERENCES "Step04".Product_Categories (id);

ALTER TABLE "Step04".Products
 ADD CONSTRAINT FK3_Products 
  FOREIGN KEY (PackProduct) 
  REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Products
    ADD CONSTRAINT FK4_Products FOREIGN KEY (TaxCat) REFERENCES "Step04".Tax_Categories (id);

ALTER TABLE "Step04".Product_coms
    ADD CONSTRAINT FK1_Product_coms FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Product_coms
    ADD CONSTRAINT FK2_Product_coms FOREIGN KEY (Product2) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Product_kits
    ADD CONSTRAINT FK1_Product_kits FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Product_kits
    ADD CONSTRAINT FK2_Product_kits FOREIGN KEY (Product_kit) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Receipts -- changed references
    ADD CONSTRAINT FK_Receipts FOREIGN KEY (id) REFERENCES "Step04".Sales (id);

ALTER TABLE "Step04".Shift_breaks
    ADD CONSTRAINT FK1_Shift_breaks FOREIGN KEY (BreakId) REFERENCES "Step04".breaks (id);

ALTER TABLE "Step04".Shift_breaks
    ADD CONSTRAINT FK2_Shift_breaks FOREIGN KEY (ShiftId) REFERENCES "Step04".Shifts (id);

ALTER TABLE "Step04".Stock_Changes
    ADD CONSTRAINT FK1_Stock_Changes FOREIGN KEY (Location) REFERENCES "Step04".Locations (id);

ALTER TABLE "Step04".Stock_Changes
    ADD CONSTRAINT FK2_Stock_Changes FOREIGN KEY (ProductId) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Stock_Currents
    ADD CONSTRAINT FK2_Stock_Currents FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Stock_Currents
    ADD CONSTRAINT FK3_Stock_Currents FOREIGN KEY (Location) REFERENCES "Step04".Locations (id);

ALTER TABLE "Step04".Stock_Diaries
    ADD CONSTRAINT FK2_Stock_Diaries FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Stock_Diaries
    ADD CONSTRAINT FK3_Stock_Diaries FOREIGN KEY (Location) REFERENCES "Step04".Locations (id);

ALTER TABLE "Step04".Stock_Levels
    ADD CONSTRAINT FK1_Stock_Levels FOREIGN KEY (Location) REFERENCES "Step04".Locations (id);

ALTER TABLE "Step04".Stock_Levels
    ADD CONSTRAINT FK2_Stock_Levels FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Taxes
    ADD CONSTRAINT FK1_Taxes FOREIGN KEY (Category) REFERENCES "Step04".Tax_Categories (id);

ALTER TABLE "Step04".Taxes
    ADD CONSTRAINT FK3_Taxes FOREIGN KEY (ParentId) REFERENCES "Step04".Taxes (id);

ALTER TABLE "Step04".Tax_Items
    ADD CONSTRAINT FK1_Tax_Items FOREIGN KEY (Receipt) REFERENCES "Step04".Receipts (id);

ALTER TABLE "Step04".Tax_Items
    ADD CONSTRAINT FK2_Tax_Items FOREIGN KEY (TaxId) REFERENCES "Step04".Taxes (id);

ALTER TABLE "Step04".Sales_items
    ADD CONSTRAINT FK2_Sales_items FOREIGN KEY (Product) REFERENCES "Step04".Products (id);

ALTER TABLE "Step04".Sales_items
    ADD CONSTRAINT FK3_Sales_items FOREIGN KEY (TaxId) REFERENCES "Step04".Taxes (id);

ALTER TABLE "Step04".Sales_items
    ADD CONSTRAINT FK4_Sales_items FOREIGN KEY (Ticket) REFERENCES "Step04".Sales (id);

ALTER TABLE "Step04".Sales
    ADD CONSTRAINT FK1_Sales FOREIGN KEY (Customer) REFERENCES "Step04".Consumers (id);

ALTER TABLE "Step04".Sales
    ADD CONSTRAINT FK2_Sales FOREIGN KEY (Person) REFERENCES "Step04".Persons (id);

--ALTER TABLE "Step04".Sales
--    ADD CONSTRAINT FK3_Sales FOREIGN KEY  (id) REFERENCES "Step04".Receipts (id);