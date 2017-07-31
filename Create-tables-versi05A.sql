-- In schema "Step05A"; several columns are renamed
-- Some columns are merged.
-- Bernaridho. 2017-07-30.

--CREATE TYPE timestamp FROM DateTime ;

CREATE TABLE "Step05A".Breaks 
(
  Id varchar(255) NOT NULL,
  Is_visible boolean DEFAULT true NOT NULL, -- previously Visible
  Name varchar(255), -- NOT NULL
  Notes varchar(255)
);


CREATE TABLE "Step05A".Closed_Cashes 
(
  Stop_timestamp timestamp, -- previously DateEnd
  Start_timestamp timestamp NOT NULL, -- previously DateStart
  Host varchar(255), -- NOT NULL
  Money varchar(255) NOT NULL,
  Sales_count integer DEFAULT 0 NOT NULL -- previously NoSales
);


CREATE TABLE "Step05A".Consumers 
(
  Address varchar(255),
  Address2 varchar(255),
  Card varchar(255),
  City varchar(255),
  Country varchar(255),
  CurDate timestamp,
  CurDebt float(52) DEFAULT 0,
  DOB timestamp,
  Discount float(52) DEFAULT 0,
  E_mail varchar(255), 
  Fax varchar(255),
  Id varchar(255) NOT NULL, -- fill value of this column with the Name value
  Image ByteA,
  Is_visible boolean DEFAULT true,
  Key4Searching varchar(255), 
  MaxDebt float(52) DEFAULT 0, -- NOT NULL
  Name varchar(255) DEFAULT ' ' NOT NULL, 
  Notes varchar(255),
  Phone varchar(255),
  Phone2 varchar(255),
  Region varchar(255),
  TaxCategory_Id varchar(255), 
  Tax_Id varchar(255), 
  Zipcode varchar(255)
);


CREATE TABLE "Step05A".CSV_Imports 
(
  Category varchar(255),
  Code varchar(255),
  CSV_Error varchar(255), 
  Id varchar(255) NOT NULL,
  Name varchar(255),
  Previous_Buy float(52), 
  Previous_Sell float(52), 
  Price_Buy float(52), 
  Price_Sell float(52), 
  Reference varchar(255),
  Record_Number varchar(255) 
);


CREATE TABLE "Step05A".Drawer_Openings 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  OpenDate timestamp default current_timestamp,
  Sales_Id varchar(255) 
);


CREATE TABLE "Step05A".Leaves 
(
  EndDate timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Name varchar(255), 
  Notes varchar(255),
  Person_Id varchar(255) NOT NULL, 
  StartDate timestamp NOT NULL
);


CREATE TABLE "Step05A".Locations 
(
  Address varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step05A".Merchants 
(
  Address text NOT NULL,
  Code varchar(8) NOT NULL,
  E_mail varchar(255) NOT NULL,
  Name varchar(255) NOT NULL,
  Phone varchar(255) NOT NULL
);


CREATE TABLE "Step05A".Payments 
(
  Card varchar(255), -- previously CardName
  Id varchar(255) NOT NULL,
  Notes varchar(255),
  Payment varchar(255) NOT NULL,
  Receipt_id varchar(255) NOT NULL, 
  Returned_Msg ByteA, 
  Tendered float(52) DEFAULT 0 NOT NULL,
  Total float(52) NOT NULL,
  Transaction_Id varchar(255) 
);


CREATE TABLE "Step05A".Persons 
(
  App_Password varchar(255), 
  Card varchar(255),
  E_mail varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Is_visible boolean,
  Name varchar(255),
  Role_id varchar(255) NOT NULL 
);


CREATE TABLE "Step05A".Product_Categories 
(
  Colour varchar(50),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Is_name_in_catalog boolean DEFAULT true NOT NULL, 
  Name varchar(255), 
  Order_in_catalog integer,  
  Parent_Id varchar(255), 
  Text_Tip varchar(255) DEFAULT NULL 
);


CREATE TABLE "Step05A".Product_coms 
(
  Id varchar(255) NOT NULL,
  Product_id varchar(255) NOT NULL, -- previously Product 
  Product2_id varchar(255) NOT NULL -- previously Product2
);


CREATE TABLE "Step05A".Product_kits 
(
  Id varchar(255) NOT NULL,
  Product_id varchar(255) NOT NULL, -- previously Product
  Product_kit varchar(255) NOT NULL,
  Quantity float(52) NOT NULL
);


CREATE TABLE "Step05A".Products 
(
  Alias varchar(50),
  Category_id varchar(255) NOT NULL, 
  Code varchar(255), -- NOT NULL
  CodeType varchar(50),
  Discounted varchar(5) DEFAULT 'no',
  Display varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Is_All_Products boolean DEFAULT false, 
  Is_Always_Available boolean DEFAULT false NOT NULL, 
  Is_Catalog bit, 
  Is_Com boolean DEFAULT false,  
  Is_Discountable boolean DEFAULT true NOT NULL, 
  Is_Managed_in_Stock boolean DEFAULT true, 
  Is_Pack boolean DEFAULT false, 
  Is_Scale boolean DEFAULT false, 
  Is_sent boolean DEFAULT false NOT NULL,
  Is_Service boolean DEFAULT false, 
  Is_Volume_Price boolean DEFAULT false, 
  Is_warranted boolean DEFAULT false NOT NULL, 
  Name varchar(255) NOT NULL,
  Order_in_catalog integer, 
  PackProduct_id varchar(255), 
  Pack_Quantity float(52), 
  Price_Buy float(52) DEFAULT 0 NOT NULL, 
  Price_Sell float(52) DEFAULT 0 NOT NULL, 
  Promotion_Id varchar(50), 
  Reference varchar(255) NOT NULL,
  Stock_Cost float(52), 
  Stock_Units float(52) DEFAULT 0 NOT NULL, 
  Stock_Volume float(52), 
  TaxCategory_id varchar(255) NOT NULL, 
  Text_Tip varchar(255)
);


CREATE TABLE "Step05A".Promotions 
(
  Criteria ByteA,
  Id varchar(50) NOT NULL,
  Is_All_Products boolean DEFAULT false, 
  Is_Enabled boolean DEFAULT true, 
  Name varchar(255), -- NOT NULL
  Script ByteA NOT NULL
);


CREATE TABLE "Step05A".Purchase_orders 
(
  Auxiliary integer,
  Details varchar(255),
  Id varchar(50) NOT NULL,
  Notes varchar(255),
  Time timestamp default current_timestamp, -- Plan B: Order_Time
  Quantity integer, 
  Sequence integer DEFAULT 0 NOT NULL,
  TicketId varchar(255)
);


CREATE TABLE "Step05A".Receipts 
(
  Date timestamp NOT NULL, -- previously DateNew
  Id varchar(255) NOT NULL,
  Money varchar(255) NOT NULL,
  Person_id varchar(255)
);


CREATE TABLE "Step05A".Removed_items 
(
  Id varchar(255) NOT NULL,
  Product_Id varchar(255), 
  Product_Name varchar(255), 
  Date timestamp default current_timestamp NOT NULL, 
  Sales_Id varchar(255), 
  Units float(52) NOT NULL
);


CREATE TABLE "Step05A".Roles 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Permissions ByteA,
  Rights_Level integer DEFAULT 4 NOT NULL 
);


CREATE TABLE "Step05A".Sales 
(
  Consumer_id varchar(255), 
  Id varchar(255) NOT NULL,
  Person_id varchar(255) NOT NULL, 
  Sales_Id integer NOT NULL, 
  Sales_Type integer NOT NULL,
  Status integer DEFAULT 0 NOT NULL
);


CREATE TABLE "Step05A".Sales_items 
(
  Line integer NOT NULL,
  Price float(52) NOT NULL,
  Product_id varchar(255), 
  Refunded_Quantity float(52) DEFAULT 0, 
  Sales_id varchar(255) NOT NULL, 
  Tax_Id varchar(255) NOT NULL, 
  units float(52) NOT NULL
);


CREATE TABLE "Step05A".Shift_Breaks 
(
  Break_Id varchar(255) NOT NULL, 
  Stop_timestamp timestamp, -- previously EndTime
  Id varchar(255) NOT NULL,
  Shift_Id varchar(255) NOT NULL, 
  Start_timestamp timestamp NOT NULL -- previously StartTime
);


CREATE TABLE "Step05A".Shifts 
(
  Stop_time timestamp, -- previously EndShift
  Id varchar(255) NOT NULL,
  Person_Id varchar(255), 
  Start_time timestamp NOT NULL -- previously StartShift
);


CREATE TABLE "Step05A".Stock_Changes 
(
  BLOb_Value ByteA, 
  Changes_processed integer,
  Change_Type integer, 
  Display varchar(255),
  Columns varchar(50), 
  Id varchar(100) NOT NULL,
  Location_id varchar(255), 
  Product_Id varchar(255), 
  Text_Value varchar(255), 
  Upload_Timestamp timestamp default current_timestamp,  -- previously Upload_Time
  UserName varchar(50)
);


CREATE TABLE "Step05A".Stock_Currents 
(
  Location_id varchar(255) NOT NULL, 
  Product_id varchar(255) NOT NULL, 
  units float(52) DEFAULT 0 NOT NULL
);


CREATE TABLE "Step05A".Stock_Diaries 
(
  App_User varchar(255), 
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Location_id varchar(255) NOT NULL, 
  Price float(52) NOT NULL,
  Product_id varchar(255) NOT NULL, 
  reason integer NOT NULL,
  units float(52) NOT NULL
);


CREATE TABLE "Step05A".Stock_Levels 
(
  Id varchar(255) NOT NULL,
  Location_id varchar(255) NOT NULL, 
  Maximum float(52) NOT NULL, 
  Product_id varchar(255) NOT NULL, 
  Security float(52) NOT NULL 
);


CREATE TABLE "Step05A".Tax_Categories 
(
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step05A".Tax_Items 
(
  Amount float(52) DEFAULT 0 NOT NULL,
  Base float(52) DEFAULT 0 NOT NULL,
  Id varchar(255) NOT NULL,
  Receipt_id varchar(255) NOT NULL, 
  Tax_Id varchar(255) NOT NULL 
);


CREATE TABLE "Step05A".Taxes 
(
  Category_id varchar(255) NOT NULL, 
  Consumer_Category_id varchar(255), -- unused at the moment
  Id varchar(255) NOT NULL,
  Is_Cascading_Rate boolean DEFAULT false NOT NULL, 
  Name varchar(255), -- fill with Id value
  Parent_Id varchar(255), 
  Rate float(52) DEFAULT 0 NOT NULL,
  Rate_Order integer 
);


CREATE TABLE "Step05A".Third_Parties 
(
  Address varchar(255),
  CIF varchar(255) NOT NULL,
  Contact_Com varchar(255), 
  Contact_Fact varchar(255), 
  E_mail varchar(255), 
  Fax varchar(255), 
  Id varchar(255) NOT NULL,
  Mobile_Phone varchar(255), 
  Name varchar(255), -- fill with Id value
  Notes varchar(255),
  Payment_Rule varchar(255), 
  Phone varchar(255), 
  website varchar(255) 
);


CREATE TABLE "Step05A".Vouchers 
(
  Redeemed_Date timestamp, 
  Redeemed_Sales_Id varchar(50), 
  Sold_Date timestamp default current_timestamp, 
  Sold_Sales_Id varchar(50), 
  Name varchar(50) NOT NULL 
);
