ALTER TABLE "Step01A".Breaks ADD CONSTRAINT PK_breaks PRIMARY KEY ("Id");

ALTER TABLE "Step01A".Categories ADD CONSTRAINT PK_categories PRIMARY KEY (id);

ALTER TABLE "Step01A".ClosedCash ADD CONSTRAINT PK_ClosedCash PRIMARY KEY (money);

ALTER TABLE "Step01A".CSVImport ADD CONSTRAINT PK_CSVImport PRIMARY KEY (id);

ALTER TABLE "Step01A".Customers ADD CONSTRAINT PK_Customers PRIMARY KEY (id);

ALTER TABLE "Step01A".DrawerOpened ADD CONSTRAINT PK_DrawerOpened PRIMARY KEY (id);

ALTER TABLE "Step01A".Leaves ADD CONSTRAINT PK_Leaves PRIMARY KEY (id);

ALTER TABLE "Step01A".LineRemoved ADD CONSTRAINT PK_LineRemoved PRIMARY KEY (id);

ALTER TABLE "Step01A".Locations ADD CONSTRAINT PK_locations PRIMARY KEY (id);

ALTER TABLE "Step01A".Orders ADD CONSTRAINT PK_orders PRIMARY KEY (id);

ALTER TABLE "Step01A".Payments ADD CONSTRAINT PK_payments PRIMARY KEY (id);

ALTER TABLE "Step01A".People ADD CONSTRAINT PK_people PRIMARY KEY (id);

ALTER TABLE "Step01A".Merchants ADD CONSTRAINT PK_Merchants PRIMARY KEY (code);

ALTER TABLE "Step01A".Places ADD CONSTRAINT PK_Places PRIMARY KEY (id);

ALTER TABLE "Step01A".Products ADD CONSTRAINT PK_Products PRIMARY KEY (id);

ALTER TABLE "Step01A".Products_Com ADD CONSTRAINT PK_Products_Com PRIMARY KEY (id, product, product2);

ALTER TABLE "Step01A".Products_Kit ADD CONSTRAINT PK_Products_Kit PRIMARY KEY (id);

ALTER TABLE "Step01A".Promotions ADD CONSTRAINT PK_Promotions PRIMARY KEY (id);

ALTER TABLE "Step01A".Receipts ADD CONSTRAINT PK_Receipts PRIMARY KEY (id);

ALTER TABLE "Step01A".Roles ADD CONSTRAINT PK_Roles PRIMARY KEY (id);

ALTER TABLE "Step01A".Shift_breaks ADD CONSTRAINT PK_shift_breaks PRIMARY KEY (id);

ALTER TABLE "Step01A".Shifts ADD CONSTRAINT PK_shifts PRIMARY KEY (id);

ALTER TABLE "Step01A".StockChanges ADD CONSTRAINT PK_StockChanges PRIMARY KEY (id);

ALTER TABLE "Step01A".StockDiary ADD CONSTRAINT PK_StockDiary PRIMARY KEY (id);

ALTER TABLE "Step01A".StockLevel ADD CONSTRAINT PK_StockLevel PRIMARY KEY (id);

ALTER TABLE "Step01A".TaxCategories ADD CONSTRAINT PK_TaxCategories PRIMARY KEY (id);

ALTER TABLE "Step01A".Taxes ADD CONSTRAINT PK_Taxes PRIMARY KEY (id);

ALTER TABLE "Step01A".TaxLines ADD CONSTRAINT PK_TaxLines PRIMARY KEY (id);

ALTER TABLE "Step01A".ThirdParties ADD CONSTRAINT PK_ThirdParties PRIMARY KEY (id);

ALTER TABLE "Step01A".TicketLines ADD CONSTRAINT PK_TicketLines PRIMARY KEY (ticket, line);

ALTER TABLE "Step01A".Tickets ADD CONSTRAINT PK_Tickets PRIMARY KEY (id);

ALTER TABLE "Step01A".Vouchers ADD CONSTRAINT PK_Vouchers PRIMARY KEY (voucher);


ALTER TABLE "Step01A".Categories
 ADD CONSTRAINT FK_Categories 
  FOREIGN KEY (ParentId) 
  REFERENCES "Step01A".Categories (id)
  ON DELETE NO ACTION
  ON UPDATE NO ACTION;

ALTER TABLE "Step01A".Leaves
    ADD CONSTRAINT FK_Leaves FOREIGN KEY (PplId) 
    REFERENCES "Step01A".People (id);

ALTER TABLE "Step01A".payments
    ADD CONSTRAINT FK_payments FOREIGN KEY (receipt) REFERENCES "Step01A".Receipts (id);

ALTER TABLE "Step01A".people
    ADD CONSTRAINT FK_people FOREIGN KEY (role) REFERENCES "Step01A".Roles (id);

ALTER TABLE "Step01A".Products
    ADD CONSTRAINT FK_Products FOREIGN KEY (PromotionId) REFERENCES "Step01A".Promotions (id);

ALTER TABLE "Step01A".Products
    ADD CONSTRAINT FK2_Products FOREIGN KEY (category) REFERENCES "Step01A".Categories (id);

ALTER TABLE "Step01A".Products
 ADD CONSTRAINT FK3_Products 
  FOREIGN KEY (PackProduct) 
  REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Products
    ADD CONSTRAINT FK4_Products FOREIGN KEY (TaxCat) REFERENCES "Step01A".TaxCategories (id);

ALTER TABLE "Step01A".Products_Com
    ADD CONSTRAINT FK1_Products_Com FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Products_Com
    ADD CONSTRAINT FK2_Products_Com FOREIGN KEY (product2) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Products_Kit
    ADD CONSTRAINT FK1_Products_Kit FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Products_Kit
    ADD CONSTRAINT FK2_Products_Kit FOREIGN KEY (product_kit) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Receipts
    ADD CONSTRAINT FK_Receipts FOREIGN KEY (money) REFERENCES "Step01A".ClosedCash (money);

ALTER TABLE "Step01A".shift_breaks
    ADD CONSTRAINT FK1_shift_breaks FOREIGN KEY (BreakId) REFERENCES "Step01A".Breaks ("Id");

ALTER TABLE "Step01A".shift_breaks
    ADD CONSTRAINT FK2_shift_breaks FOREIGN KEY (ShiftId) REFERENCES "Step01A".Shifts (id);

ALTER TABLE "Step01A".StockCurrent
    ADD CONSTRAINT FK2_StockCurrent FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".StockCurrent
    ADD CONSTRAINT FK3_StockCurrent FOREIGN KEY (location) REFERENCES "Step01A".Locations (id);

ALTER TABLE "Step01A".StockDiary
    ADD CONSTRAINT FK2_StockDiary FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".StockDiary
    ADD CONSTRAINT FK3_StockDiary FOREIGN KEY (location) REFERENCES "Step01A".Locations (id);

ALTER TABLE "Step01A".StockLevel
    ADD CONSTRAINT FK1_StockLevel FOREIGN KEY (location) REFERENCES "Step01A".Locations (id);

ALTER TABLE "Step01A".StockLevel
    ADD CONSTRAINT FK2_StockLevel FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".Taxes
    ADD CONSTRAINT FK1_Taxes FOREIGN KEY (category) REFERENCES "Step01A".TaxCategories (id);

ALTER TABLE "Step01A".Taxes
    ADD CONSTRAINT FK3_Taxes FOREIGN KEY (ParentId) REFERENCES "Step01A".Taxes (id);

ALTER TABLE "Step01A".TaxLines
    ADD CONSTRAINT FK1_TaxLines FOREIGN KEY (receipt) REFERENCES "Step01A".Receipts (id);

ALTER TABLE "Step01A".TaxLines
    ADD CONSTRAINT FK2_TaxLines FOREIGN KEY (TaxId) REFERENCES "Step01A".Taxes (id);

ALTER TABLE "Step01A".TicketLines
    ADD CONSTRAINT FK2_TicketLines FOREIGN KEY (product) REFERENCES "Step01A".Products (id);

ALTER TABLE "Step01A".TicketLines
    ADD CONSTRAINT FK3_TicketLines FOREIGN KEY (TaxId) REFERENCES "Step01A".Taxes (id);

ALTER TABLE "Step01A".TicketLines
    ADD CONSTRAINT FK4_TicketLines FOREIGN KEY (ticket) REFERENCES "Step01A".Tickets (id);

ALTER TABLE "Step01A".Tickets
    ADD CONSTRAINT FK1_Tickets FOREIGN KEY (customer) REFERENCES "Step01A".Customers (id);

ALTER TABLE "Step01A".Tickets
    ADD CONSTRAINT FK2_Tickets FOREIGN KEY (person) REFERENCES "Step01A".People (id);

ALTER TABLE "Step01A".Tickets
    ADD CONSTRAINT FK3_Tickets FOREIGN KEY  (id) REFERENCES "Step01A".Receipts (id);