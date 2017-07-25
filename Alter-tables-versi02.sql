ALTER TABLE "Step02".Breaks ADD CONSTRAINT PK_breaks PRIMARY KEY ("Id");

ALTER TABLE "Step02".Categories ADD CONSTRAINT PK_categories PRIMARY KEY (id);

ALTER TABLE "Step02".ClosedCash ADD CONSTRAINT PK_ClosedCash PRIMARY KEY (money);

ALTER TABLE "Step02".CSVImport ADD CONSTRAINT PK_CSVImport PRIMARY KEY (id);

ALTER TABLE "Step02".Customers ADD CONSTRAINT PK_Customers PRIMARY KEY (id);

ALTER TABLE "Step02".DrawerOpened ADD CONSTRAINT PK_DrawerOpened PRIMARY KEY (id);

ALTER TABLE "Step02".Leaves ADD CONSTRAINT PK_Leaves PRIMARY KEY (id);

ALTER TABLE "Step02".LineRemoved ADD CONSTRAINT PK_LineRemoved PRIMARY KEY (id);

ALTER TABLE "Step02".Locations ADD CONSTRAINT PK_locations PRIMARY KEY (id);

ALTER TABLE "Step02".Orders ADD CONSTRAINT PK_orders PRIMARY KEY (id);

ALTER TABLE "Step02".Payments ADD CONSTRAINT PK_payments PRIMARY KEY (id);

ALTER TABLE "Step02".People ADD CONSTRAINT PK_people PRIMARY KEY (id);

ALTER TABLE "Step02".Merchants ADD CONSTRAINT PK_Merchants PRIMARY KEY (code);

ALTER TABLE "Step02".Places ADD CONSTRAINT PK_Places PRIMARY KEY (id);

ALTER TABLE "Step02".Products ADD CONSTRAINT PK_Products PRIMARY KEY (id);

ALTER TABLE "Step02".Products_Com ADD CONSTRAINT PK_Products_Com PRIMARY KEY (id, product, product2);

ALTER TABLE "Step02".Products_Kit ADD CONSTRAINT PK_Products_Kit PRIMARY KEY (id);

ALTER TABLE "Step02".Promotions ADD CONSTRAINT PK_Promotions PRIMARY KEY (id);

ALTER TABLE "Step02".Receipts ADD CONSTRAINT PK_Receipts PRIMARY KEY (id);

ALTER TABLE "Step02".Roles ADD CONSTRAINT PK_Roles PRIMARY KEY (id);

ALTER TABLE "Step02".Shift_breaks ADD CONSTRAINT PK_shift_breaks PRIMARY KEY (id);

ALTER TABLE "Step02".Shifts ADD CONSTRAINT PK_shifts PRIMARY KEY (id);

ALTER TABLE "Step02".StockChanges ADD CONSTRAINT PK_StockChanges PRIMARY KEY (id);

ALTER TABLE "Step02".StockDiary ADD CONSTRAINT PK_StockDiary PRIMARY KEY (id);

ALTER TABLE "Step02".StockLevel ADD CONSTRAINT PK_StockLevel PRIMARY KEY (id);

ALTER TABLE "Step02".TaxCategories ADD CONSTRAINT PK_TaxCategories PRIMARY KEY (id);

ALTER TABLE "Step02".Taxes ADD CONSTRAINT PK_Taxes PRIMARY KEY (id);

ALTER TABLE "Step02".TaxLines ADD CONSTRAINT PK_TaxLines PRIMARY KEY (id);

ALTER TABLE "Step02".ThirdParties ADD CONSTRAINT PK_ThirdParties PRIMARY KEY (id);

ALTER TABLE "Step02".Sales ADD CONSTRAINT PK_Sales PRIMARY KEY (id);

ALTER TABLE "Step02".Sales_items ADD CONSTRAINT PK_Sales_items PRIMARY KEY (ticket, line);

ALTER TABLE "Step02".Vouchers ADD CONSTRAINT PK_Vouchers PRIMARY KEY (voucher);


ALTER TABLE "Step02".Categories
 ADD CONSTRAINT FK_Categories 
  FOREIGN KEY (ParentId) 
  REFERENCES "Step02".Categories (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE "Step02".Leaves
    ADD CONSTRAINT FK_Leaves FOREIGN KEY (PplId) 
    REFERENCES "Step02".People (id);

ALTER TABLE "Step02".Payments
    ADD CONSTRAINT FK_payments FOREIGN KEY (receipt) REFERENCES "Step02".Receipts (id);

ALTER TABLE "Step02".People
    ADD CONSTRAINT FK_people FOREIGN KEY (role) REFERENCES "Step02".Roles (id);

ALTER TABLE "Step02".Products
    ADD CONSTRAINT FK_Products FOREIGN KEY (PromotionId) REFERENCES "Step02".Promotions (id);

ALTER TABLE "Step02".Products
    ADD CONSTRAINT FK2_Products FOREIGN KEY (category) REFERENCES "Step02".Categories (id);

ALTER TABLE "Step02".Products
 ADD CONSTRAINT FK3_Products 
  FOREIGN KEY (PackProduct) 
  REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Products
    ADD CONSTRAINT FK4_Products FOREIGN KEY (TaxCat) REFERENCES "Step02".TaxCategories (id);

ALTER TABLE "Step02".Products_Com
    ADD CONSTRAINT FK1_Products_Com FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Products_Com
    ADD CONSTRAINT FK2_Products_Com FOREIGN KEY (product2) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Products_Kit
    ADD CONSTRAINT FK1_Products_Kit FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Products_Kit
    ADD CONSTRAINT FK2_Products_Kit FOREIGN KEY (product_kit) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Receipts
    ADD CONSTRAINT FK_Receipts FOREIGN KEY (money) REFERENCES "Step02".ClosedCash (money);

ALTER TABLE "Step02".Shift_breaks
    ADD CONSTRAINT FK1_shift_breaks FOREIGN KEY (BreakId) REFERENCES "Step02".Breaks ("Id");

ALTER TABLE "Step02".Shift_breaks
    ADD CONSTRAINT FK2_shift_breaks FOREIGN KEY (ShiftId) REFERENCES "Step02".Shifts (id);

ALTER TABLE "Step02".StockCurrent
    ADD CONSTRAINT FK2_StockCurrent FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".StockCurrent
    ADD CONSTRAINT FK3_StockCurrent FOREIGN KEY (location) REFERENCES "Step02".Locations (id);

ALTER TABLE "Step02".StockDiary
    ADD CONSTRAINT FK2_StockDiary FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".StockDiary
    ADD CONSTRAINT FK3_StockDiary FOREIGN KEY (location) REFERENCES "Step02".Locations (id);

ALTER TABLE "Step02".StockLevel
    ADD CONSTRAINT FK1_StockLevel FOREIGN KEY (location) REFERENCES "Step02".Locations (id);

ALTER TABLE "Step02".StockLevel
    ADD CONSTRAINT FK2_StockLevel FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Taxes
    ADD CONSTRAINT FK1_Taxes FOREIGN KEY (category) REFERENCES "Step02".TaxCategories (id);

ALTER TABLE "Step02".Taxes
    ADD CONSTRAINT FK3_Taxes FOREIGN KEY (ParentId) REFERENCES "Step02".Taxes (id);

ALTER TABLE "Step02".TaxLines
    ADD CONSTRAINT FK1_TaxLines FOREIGN KEY (receipt) REFERENCES "Step02".Receipts (id);

ALTER TABLE "Step02".TaxLines
    ADD CONSTRAINT FK2_TaxLines FOREIGN KEY (TaxId) REFERENCES "Step02".Taxes (id);

ALTER TABLE "Step02".Sales_items
    ADD CONSTRAINT FK2_Sales_items FOREIGN KEY (product) REFERENCES "Step02".Products (id);

ALTER TABLE "Step02".Sales_items
    ADD CONSTRAINT FK3_Sales_items FOREIGN KEY (TaxId) REFERENCES "Step02".Taxes (id);

ALTER TABLE "Step02".Sales_items
    ADD CONSTRAINT FK4_Sales_items FOREIGN KEY (ticket) REFERENCES "Step02".Sales (id);

ALTER TABLE "Step02".Sales
    ADD CONSTRAINT FK1_Sales FOREIGN KEY (customer) REFERENCES "Step02".Customers (id);

ALTER TABLE "Step02".Sales
    ADD CONSTRAINT FK2_Sales FOREIGN KEY (person) REFERENCES "Step02".People (id);

ALTER TABLE "Step02".Sales
    ADD CONSTRAINT FK3_Sales FOREIGN KEY  (id) REFERENCES "Step02".Receipts (id);