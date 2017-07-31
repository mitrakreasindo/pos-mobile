ALTER TABLE "Step03".Breaks ADD CONSTRAINT PK_Breaks PRIMARY KEY ("Id");

ALTER TABLE "Step03".Product_Categories ADD CONSTRAINT PK_Product_Categories PRIMARY KEY (id);

ALTER TABLE "Step03".Closed_Cashes ADD CONSTRAINT PK_Closed_Cashes PRIMARY KEY (money);

ALTER TABLE "Step03".CSV_imports ADD CONSTRAINT PK_CSV_imports PRIMARY KEY (id);

ALTER TABLE "Step03".Consumers ADD CONSTRAINT PK_Consumers PRIMARY KEY (id);

ALTER TABLE "Step03".Drawer_Openings ADD CONSTRAINT PK_Drawer_Openings PRIMARY KEY (id);

ALTER TABLE "Step03".Leaves ADD CONSTRAINT PK_Leaves PRIMARY KEY (id);

ALTER TABLE "Step03".Removed_Items ADD CONSTRAINT PK_Removed_Items PRIMARY KEY (id);

ALTER TABLE "Step03".Locations ADD CONSTRAINT PK_Locations PRIMARY KEY (id);

ALTER TABLE "Step03".Purchase_orders ADD CONSTRAINT PK_Purchase_orders PRIMARY KEY (id);

ALTER TABLE "Step03".Payments ADD CONSTRAINT PK_Payments PRIMARY KEY (id);

ALTER TABLE "Step03".Persons ADD CONSTRAINT PK_Persons PRIMARY KEY (id);

ALTER TABLE "Step03".Merchants ADD CONSTRAINT PK_Merchants PRIMARY KEY (code);

ALTER TABLE "Step03".Places ADD CONSTRAINT PK_Places PRIMARY KEY (id);

ALTER TABLE "Step03".Products ADD CONSTRAINT PK_Products PRIMARY KEY (id);

ALTER TABLE "Step03".Product_coms ADD CONSTRAINT PK_Product_coms PRIMARY KEY (id, product, product2);

ALTER TABLE "Step03".Product_kits ADD CONSTRAINT PK_Product_kits PRIMARY KEY (id);

ALTER TABLE "Step03".Promotions ADD CONSTRAINT PK_Promotions PRIMARY KEY (id);

ALTER TABLE "Step03".Receipts ADD CONSTRAINT PK_Receipts PRIMARY KEY (id);

ALTER TABLE "Step03".Roles ADD CONSTRAINT PK_Roles PRIMARY KEY (id);

ALTER TABLE "Step03".Shift_breaks ADD CONSTRAINT PK_Shift_breaks PRIMARY KEY (id);

ALTER TABLE "Step03".Shifts ADD CONSTRAINT PK_Shifts PRIMARY KEY (id);

ALTER TABLE "Step03".Stock_Changes ADD CONSTRAINT PK_Stock_Changes PRIMARY KEY (id);

ALTER TABLE "Step03".Stock_Diaries ADD CONSTRAINT PK_Stock_Diaries PRIMARY KEY (id);

ALTER TABLE "Step03".Stock_Levels ADD CONSTRAINT PK_Stock_Levels PRIMARY KEY (id);

ALTER TABLE "Step03".Tax_Categories ADD CONSTRAINT PK_Tax_Categories PRIMARY KEY (id);

ALTER TABLE "Step03".Taxes ADD CONSTRAINT PK_Taxes PRIMARY KEY (id);

ALTER TABLE "Step03".Tax_Items ADD CONSTRAINT PK_Tax_Items PRIMARY KEY (id);

ALTER TABLE "Step03".Third_Parties ADD CONSTRAINT PK_Third_Parties PRIMARY KEY (id);

ALTER TABLE "Step03".Sales ADD CONSTRAINT PK_Sales PRIMARY KEY (id);

ALTER TABLE "Step03".Sales_items ADD CONSTRAINT PK_Sales_items PRIMARY KEY (ticket, line);

ALTER TABLE "Step03".Vouchers ADD CONSTRAINT PK_Vouchers PRIMARY KEY (voucher);


ALTER TABLE "Step03".Product_Categories
 ADD CONSTRAINT FK_Categories 
  FOREIGN KEY (ParentId) 
  REFERENCES "Step03".Product_Categories (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE "Step03".Leaves
    ADD CONSTRAINT FK_Leaves FOREIGN KEY (PplId) 
    REFERENCES "Step03".Persons (id);

ALTER TABLE "Step03".Payments
    ADD CONSTRAINT FK_payments FOREIGN KEY (receipt) REFERENCES "Step03".Receipts (id);

ALTER TABLE "Step03".Persons
    ADD CONSTRAINT FK_people FOREIGN KEY (role) REFERENCES "Step03".Roles (id);

ALTER TABLE "Step03".Products
    ADD CONSTRAINT FK_Products FOREIGN KEY (PromotionId) REFERENCES "Step03".Promotions (id);

ALTER TABLE "Step03".Products
    ADD CONSTRAINT FK2_Products FOREIGN KEY (category) REFERENCES "Step03".Product_Categories (id);

ALTER TABLE "Step03".Products
 ADD CONSTRAINT FK3_Products 
  FOREIGN KEY (PackProduct) 
  REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Products
    ADD CONSTRAINT FK4_Products FOREIGN KEY (TaxCat) REFERENCES "Step03".Tax_Categories (id);

ALTER TABLE "Step03".Product_coms
    ADD CONSTRAINT FK1_Product_coms FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Product_coms
    ADD CONSTRAINT FK2_Product_coms FOREIGN KEY (product2) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Product_kits
    ADD CONSTRAINT FK1_Product_kits FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Product_kits
    ADD CONSTRAINT FK2_Product_kits FOREIGN KEY (product_kit) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Receipts
    ADD CONSTRAINT FK_Receipts FOREIGN KEY (money) REFERENCES "Step03".Closed_Cashes (money);

ALTER TABLE "Step03".Shift_breaks
    ADD CONSTRAINT FK1_shift_breaks FOREIGN KEY (BreakId) REFERENCES "Step03".Breaks ("Id");

ALTER TABLE "Step03".Shift_breaks
    ADD CONSTRAINT FK2_shift_breaks FOREIGN KEY (ShiftId) REFERENCES "Step03".Shifts (id);

ALTER TABLE "Step03".StockCurrent
    ADD CONSTRAINT FK2_StockCurrent FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".StockCurrent
    ADD CONSTRAINT FK3_StockCurrent FOREIGN KEY (location) REFERENCES "Step03".Locations (id);

ALTER TABLE "Step03".Stock_Diaries
    ADD CONSTRAINT FK2_Stock_Diaries FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Stock_Diaries
    ADD CONSTRAINT FK3_Stock_Diaries FOREIGN KEY (location) REFERENCES "Step03".Locations (id);

ALTER TABLE "Step03".Stock_Levels
    ADD CONSTRAINT FK1_Stock_Levels FOREIGN KEY (location) REFERENCES "Step03".Locations (id);

ALTER TABLE "Step03".Stock_Levels
    ADD CONSTRAINT FK2_Stock_Levels FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Taxes
    ADD CONSTRAINT FK1_Taxes FOREIGN KEY (category) REFERENCES "Step03".Tax_Categories (id);

ALTER TABLE "Step03".Taxes
    ADD CONSTRAINT FK3_Taxes FOREIGN KEY (ParentId) REFERENCES "Step03".Taxes (id);

ALTER TABLE "Step03".Tax_Items
    ADD CONSTRAINT FK1_Tax_Items FOREIGN KEY (receipt) REFERENCES "Step03".Receipts (id);

ALTER TABLE "Step03".Tax_Items
    ADD CONSTRAINT FK2_Tax_Items FOREIGN KEY (TaxId) REFERENCES "Step03".Taxes (id);

ALTER TABLE "Step03".Sales_items
    ADD CONSTRAINT FK2_Sales_items FOREIGN KEY (product) REFERENCES "Step03".Products (id);

ALTER TABLE "Step03".Sales_items
    ADD CONSTRAINT FK3_Sales_items FOREIGN KEY (TaxId) REFERENCES "Step03".Taxes (id);

ALTER TABLE "Step03".Sales_items
    ADD CONSTRAINT FK4_Sales_items FOREIGN KEY (ticket) REFERENCES "Step03".Sales (id);

ALTER TABLE "Step03".Sales
    ADD CONSTRAINT FK1_Sales FOREIGN KEY (customer) REFERENCES "Step03".Consumers (id);

ALTER TABLE "Step03".Sales
    ADD CONSTRAINT FK2_Sales FOREIGN KEY (person) REFERENCES "Step03".Persons (id);

ALTER TABLE "Step03".Sales
    ADD CONSTRAINT FK3_Sales FOREIGN KEY  (id) REFERENCES "Step03".Receipts (id);