-- In schema "Step05"; several columns are renamed
-- Some columns are merged.
-- Bernaridho. 2017-07-29.

--CREATE TYPE timestamp FROM DateTime ;

CREATE TABLE "Step05".Breaks 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  Is_visible boolean DEFAULT true NOT NULL -- previously Visible
);


CREATE TABLE "Step05".Closed_Cashes 
(
  DateEnd timestamp,
  DateStart timestamp NOT NULL,
  Host varchar(255), -- NOT NULL
--HostSequence integer, --  NOT NULL
  Money varchar(255) NOT NULL,
  Sales_count integer DEFAULT 0 NOT NULL -- previously NoSales
);


CREATE TABLE "Step05".Consumers 
(
  Address varchar(255),
  Address2 varchar(255),
  Card varchar(255),
  City varchar(255),
  Country varchar(255),
  CurDate timestamp,
  CurDebt float(52) DEFAULT 0,
  Discount float(52) DEFAULT 0,
  DOB timestamp,
  E_mail varchar(255), -- previously Email
  Fax varchar(255),
--FirstName varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
--LastName varchar(255),
  MaxDebt float(52) DEFAULT 0, -- NOT NULL
  Name varchar(255) DEFAULT ' ' NOT NULL, -- fill value of this column with
  Notes varchar(255),
  Phone varchar(255),
  Phone2 varchar(255),
  Zipcode varchar(255), -- previously Postal
  Region varchar(255),
  Key4Searching varchar(255), -- NOT NULL previously SearchKey
  TaxCategory_Id varchar(255), -- previously TaxCategory
  Tax_Id varchar(255), -- previously TaxId
  Is_visible boolean DEFAULT true -- boolean NOT NULL previously Visible
);


CREATE TABLE "Step05".CSV_Imports 
(
  Category varchar(255),
  Code varchar(255),
  CSV_Error varchar(255), -- previously CSVError
  Id varchar(255) NOT NULL,
  Name varchar(255),
  Previous_Buy float(52), -- previously PreviousBuy
  Previous_Sell float(52), -- previously PreviousSell
  Price_Buy float(52), -- previously PriceBuy
  Price_Sell float(52), -- previously PriceSell
  Reference varchar(255),
  Record_Number varchar(255) -- previously RowNumber
);


CREATE TABLE "Step05".Drawer_Openings 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  OpenDate timestamp default current_timestamp,
  Sales_Id varchar(255) -- previously TicketId
);


CREATE TABLE "Step05".Leaves 
(
  EndDate timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  Person_Id varchar(255) NOT NULL, -- previously PplId
  StartDate timestamp NOT NULL
);


CREATE TABLE "Step05".Locations 
(
  Address varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step05".Merchants 
(
  Address text NOT NULL,
  Code varchar(8) NOT NULL,
  E_mail varchar(255) NOT NULL,
  Name varchar(255) NOT NULL,
  Phone varchar(255) NOT NULL
);


CREATE TABLE "Step05".Payments 
(
  CardName varchar(255),
  Id varchar(255) NOT NULL,
  Notes varchar(255),
  Payment varchar(255) NOT NULL,
  Receipt_id varchar(255) NOT NULL, -- previously Receipt
  Returned_Msg ByteA, -- previously ReturnMsg
  Tendered float(52) DEFAULT 0 NOT NULL,
  Total float(52) NOT NULL,
  Transaction_Id varchar(255) -- previously TransId
);


CREATE TABLE "Step05".Persons 
(
  App_Password varchar(255), -- previously AppPassword
  Card varchar(255),
  E_mail varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255),
  Role_id varchar(255) NOT NULL, -- previously Role
  Is_visible boolean -- boolean NOT NULL; previously Visible
);


CREATE TABLE "Step05".Product_Categories 
(
  Order_in_catalog integer, -- previously CatOrder 
  Is_name_in_catalog boolean DEFAULT true NOT NULL, -- boolean; previously CatShowName
  Colour varchar(50),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255), -- NOT NULL
  Parent_Id varchar(255), -- previously ParentId
  Text_Tip varchar(255) DEFAULT NULL -- previously TextTip
);


CREATE TABLE "Step05".Product_coms 
(
  Id varchar(255) NOT NULL,
  Product_id varchar(255) NOT NULL, -- previously Product 
  Product2_id varchar(255) NOT NULL -- previously Product2
);


CREATE TABLE "Step05".Product_kits 
(
  Id varchar(255) NOT NULL,
  Product_id varchar(255) NOT NULL, -- previously Product
  Product_kit varchar(255) NOT NULL,
  Quantity float(52) NOT NULL
);


CREATE TABLE "Step05".Products 
(
  Alias varchar(50),
  Is_All_Products boolean DEFAULT false, -- boolean; previously AllProducts
  Is_Always_Available boolean DEFAULT false NOT NULL, -- boolean; previously AlwaysAvailable
--Attributes ByteA,
--AttributeSet_id varchar(255),
  Is_Discountable boolean DEFAULT true NOT NULL, -- boolean; previously CanDiscount
  Category_id varchar(255) NOT NULL, -- previously Category
  Order_in_catalog integer, -- previously CatOrder
  Code varchar(255), -- NOT NULL
  CodeType varchar(50),
  Discounted varchar(5) DEFAULT 'no',
  Display varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Is_Catalog bit, -- previously IsCatalog
  Is_Com boolean DEFAULT false, -- previously IsCom
--IsKitchen boolean DEFAULT false, -- previously 
  Is_Pack boolean DEFAULT false, -- previously IsPack
  Is_Scale boolean DEFAULT false, -- previously IsScale
  Is_Service boolean DEFAULT false, -- previously IsService
--IsVerPAtrib boolean DEFAULT false, -- previously 
  Is_Volume_Price boolean DEFAULT false, -- previously IsVPrice
  Is_Managed_in_Stock boolean DEFAULT true, -- previously ManageStock
  Name varchar(255) NOT NULL,
  PackProduct_id varchar(255), -- previously PackProduct
  Pack_Quantity float(52), -- previously PackQuantity
  Price_Buy float(52) DEFAULT 0 NOT NULL, -- previously PriceBuy
  Price_Sell float(52) DEFAULT 0 NOT NULL, -- previously PriceSell
--Print_Kb boolean DEFAULT false NOT NULL, -- boolean; previously PrintKb
  Promotion_Id varchar(50), -- previously PromotionId
  Reference varchar(255) NOT NULL,
  Is_Sent boolean DEFAULT false NOT NULL, -- boolean; previously SendStatus
  Stock_Cost float(52), -- previously StockCost
  Stock_Units float(52) DEFAULT 0 NOT NULL, -- previously StockUnits
  Stock_Volume float(52), -- previously StockVolume
  TaxCategory_id varchar(255) NOT NULL, -- previously TaxCat
  Text_Tip varchar(255), -- previously TextTip
  Is_warranted boolean DEFAULT false NOT NULL -- boolean; previously warranty
);


CREATE TABLE "Step05".Promotions 
(
  Is_All_Products boolean DEFAULT false, -- boolean; previously AllProducts
  Criteria ByteA,
  Id varchar(50) NOT NULL,
  Is_Enabled boolean DEFAULT true, -- boolean previously IsEnabled
  Name varchar(255), -- NOT NULL
  Script ByteA NOT NULL
);


CREATE TABLE "Step05".Purchase_orders 
(
--Attributes varchar(255),
  Auxiliary integer,
  Details varchar(255),
--DisplayId integer,
  Id varchar(50) NOT NULL,
  Notes varchar(255),
--OrderId varchar(50),
  Time timestamp default current_timestamp, -- previously OrderTime
  Quantity integer, -- previously Qty
  Sequence integer DEFAULT 0 NOT NULL,
  TicketId varchar(255)
);


CREATE TABLE "Step05".Receipts 
(
--Attributes ByteA,
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Money varchar(255) NOT NULL,
  Person_id varchar(255) -- previously Person
);


CREATE TABLE "Step05".Removed_items 
(
  Id varchar(255) NOT NULL,
--Name varchar(255),
  Product_Id varchar(255), -- previously ProductId
  Product_Name varchar(255), -- previously ProductName
  Date timestamp default current_timestamp NOT NULL, -- previously RemovedDate
  Sales_Id varchar(255), -- previously TicketId
  Units float(52) NOT NULL
);


CREATE TABLE "Step05".Roles 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Permissions ByteA,
  Rights_Level integer DEFAULT 4 NOT NULL -- previously RightsLevel
);


CREATE TABLE "Step05".Sales 
(
  Consumer_id varchar(255), -- previously Customer
  Id varchar(255) NOT NULL,
  Person_id varchar(255) NOT NULL, -- previously Person
  Status integer DEFAULT 0 NOT NULL,
  Sales_Id integer NOT NULL, -- previously TicketId
  Sales_Type integer NOT NULL -- previously TicketType
);


CREATE TABLE "Step05".Sales_items 
(
--Attributes ByteA,
--AttributeSetInstance_Id varchar(255),
  Line integer NOT NULL,
  Price float(52) NOT NULL,
  Product_id varchar(255), -- previously Product
  Refunded_Quantity float(52) DEFAULT 0, -- previously RefundQty
  Tax_Id varchar(255) NOT NULL, -- previously TaxId
  Sales_id varchar(255) NOT NULL, -- previously Ticket
  units float(52) NOT NULL
);


CREATE TABLE "Step05".Shift_Breaks 
(
  Break_Id varchar(255) NOT NULL, -- previously BreakId
  EndTime timestamp,
  Id varchar(255) NOT NULL,
  Shift_Id varchar(255) NOT NULL, -- previously ShiftId
  StartTime timestamp NOT NULL
);


CREATE TABLE "Step05".Shifts 
(
  EndShift timestamp,
  Id varchar(255) NOT NULL,
  Person_Id varchar(255), -- previously PplId
  StartShift timestamp NOT NULL
);


CREATE TABLE "Step05".Stock_Changes 
(
  BLOb_Value ByteA, -- previously BLObValue
  Changes_processed integer,
  Change_Type integer, -- previously ChangeType
  Display varchar(255),
  Columns varchar(50), -- previously Field
  Id varchar(100) NOT NULL,
  Location_id varchar(255), -- previously Location
  Product_Id varchar(255), -- previously ProductId
  Text_Value varchar(255), -- previously TextValue
  Upload_Time timestamp default current_timestamp, -- previously UploadTime
  UserName varchar(50)
);


CREATE TABLE "Step05".Stock_Currents 
(
--AttributeSetInstance_Id varchar(255),
  Location_id varchar(255) NOT NULL, -- previously Location
  Product_id varchar(255) NOT NULL, -- previously Product
  units float(52) DEFAULT 0 NOT NULL
);


CREATE TABLE "Step05".Stock_Diaries 
(
  App_User varchar(255), -- previously AppUser
--AttributeSetInstance_Id varchar(255),
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Location_id varchar(255) NOT NULL, -- previously Location
  Price float(52) NOT NULL,
  Product_id varchar(255) NOT NULL, -- previously Product
  reason integer NOT NULL,
  units float(52) NOT NULL
);


CREATE TABLE "Step05".Stock_Levels 
(
  Id varchar(255) NOT NULL,
  Location_id varchar(255) NOT NULL, -- previously Location
  Product_id varchar(255) NOT NULL, -- previously Product
  Maximum float(52) NOT NULL, -- previously StockMaximum
  Security float(52) NOT NULL -- previously StockSecurity
);


CREATE TABLE "Step05".Tax_Categories 
(
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step05".Tax_Items 
(
  Amount float(52) DEFAULT 0 NOT NULL,
  Base float(52) DEFAULT 0 NOT NULL,
  Id varchar(255) NOT NULL,
  Receipt_id varchar(255) NOT NULL, -- previously Receipt
  Tax_Id varchar(255) NOT NULL -- previously TaxId
);


CREATE TABLE "Step05".Taxes 
(
  Category_id varchar(255) NOT NULL, -- previously Category
  Consumer_Category_id varchar(255), -- previously CustCategory
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Parent_Id varchar(255), -- previously ParentId
  Rate float(52) DEFAULT 0 NOT NULL,
  Is_Cascading_Rate boolean DEFAULT false NOT NULL, -- boolean; previously RateCascade
  Rate_Order integer -- previously RateOrder
);


CREATE TABLE "Step05".Third_Parties 
(
  Address varchar(255),
  CIF varchar(255) NOT NULL,
  Contact_Com varchar(255), -- previously ContactComm
  Contact_Fact varchar(255), -- previously ContactFact
  E_mail varchar(255), -- previously Email
  Fax varchar(255), -- previously FaxNumber
  Id varchar(255) NOT NULL,
  Mobile_Phone varchar(255), -- previously MobileNumber
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  Payment_Rule varchar(255), -- previously PayRule
  Phone varchar(255), -- previously PhoneNumber
  website varchar(255) -- previously webpage
);


CREATE TABLE "Step05".Vouchers 
(
  Redeemed_Date timestamp, -- previously RedeemDate
  Redeemed_Sales_Id varchar(50), -- previously RedeemTicketId
  Sold_Date timestamp default current_timestamp, -- previously SoldDate
  Sold_Sales_Id varchar(50), -- previously SoldTicketId
  Name varchar(50) NOT NULL -- previously Voucher
);
