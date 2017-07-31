-- In schema "Step04"; several constraints are removed
-- Several tables are already ignored in this version
-- Several columns are dropped.
-- Bernaridho. 2017-07-27.

--CREATE TYPE timestamp FROM DateTime ;

CREATE TABLE "Step04".Breaks 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  Visible boolean DEFAULT true NOT NULL -- boolean
);


CREATE TABLE "Step04".Product_Categories 
(
  CatOrder integer,
  CatShowName boolean DEFAULT true NOT NULL, -- boolean
  Colour varchar(50),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255), -- NOT NULL
  ParentId varchar(255),
  TextTip varchar(255) DEFAULT NULL
);


CREATE TABLE "Step04".Closed_Cashes 
(
  DateEnd timestamp,
  DateStart timestamp NOT NULL,
  Host varchar(255), -- NOT NULL
  HostSequence integer, --  NOT NULL
  Money varchar(255) NOT NULL,
  NoSales integer DEFAULT 0 NOT NULL
);


CREATE TABLE "Step04".CSV_Imports 
(
  Category varchar(255),
  Code varchar(255),
  CSVError varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255),
  PreviousBuy float(52),
  PreviousSell float(52),
  PriceBuy float(52),
  PriceSell float(52),
  Reference varchar(255),
  RowNumber varchar(255)
);


CREATE TABLE "Step04".Consumers 
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
  Email varchar(255),
  Fax varchar(255),
  FirstName varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  LastName varchar(255),
  MaxDebt float(52) DEFAULT 0, -- NOT NULL
  Name varchar(255) DEFAULT ' ' NOT NULL, -- fill value of this column with
  Notes varchar(255),
  Phone varchar(255),
  Phone2 varchar(255),
  Postal varchar(255),
  Region varchar(255),
  SearchKey varchar(255), -- NOT NULL
  TaxCategory varchar(255),
  TaxId varchar(255),
  Visible boolean DEFAULT true -- boolean NOT NULL
);


CREATE TABLE "Step04".Drawer_Openings 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  OpenDate timestamp default current_timestamp,
  TicketId varchar(255)
);


CREATE TABLE "Step04".Leaves 
(
  EndDate timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  PplId varchar(255) NOT NULL,
  StartDate timestamp NOT NULL
);


CREATE TABLE "Step04".Removed_items 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  ProductId varchar(255),
  ProductName varchar(255),
  RemovedDate timestamp default current_timestamp NOT NULL,
  TicketId varchar(255),
  Units float(52) NOT NULL
);


CREATE TABLE "Step04".Locations 
(
  Address varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step04".Merchants 
(
  Address text NOT NULL,
  Code varchar(8) NOT NULL,
  Email varchar(255) NOT NULL,
  Name varchar(255) NOT NULL,
  Phone varchar(255) NOT NULL
);


CREATE TABLE "Step04".Purchase_orders 
(
  Attributes varchar(255),
  Auxiliary integer,
  Details varchar(255),
  DisplayId integer,
  Id varchar(50) NOT NULL,
  Notes varchar(255),
  OrderId varchar(50),
  OrderTime timestamp default current_timestamp,
  Qty integer,
  Sequence integer DEFAULT 0 NOT NULL,
  TicketId varchar(255)
);


CREATE TABLE "Step04".Payments 
(
  CardName varchar(255),
  Id varchar(255) NOT NULL,
  Notes varchar(255),
  Payment varchar(255) NOT NULL,
  Receipt varchar(255) NOT NULL,
  ReturnMsg ByteA,
  Tendered float(52) DEFAULT 0 NOT NULL,
  Total float(52) NOT NULL,
  TransId varchar(255)
);


CREATE TABLE "Step04".Persons 
(
  AppPassword varchar(255),
  Card varchar(255),
  Email varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255),
  Role varchar(255) NOT NULL,
  Visible bit -- boolean NOT NULL
);


CREATE TABLE "Step04".Places 
(
  Customer varchar(255),
  floor varchar(255), -- NOT NULL
  Id varchar(255) NOT NULL,
  Locked boolean DEFAULT false, -- boolean 
  Name varchar(255), -- NOT NULL
  OpenedBy varchar(50),
  TicketId varchar(255),
  waiter varchar(255),
  x integer NOT NULL,
  y integer NOT NULL
);


CREATE TABLE "Step04".Products 
(
  Alias varchar(50),
  AllProducts boolean DEFAULT false, -- boolean
  AlwaysAvailable boolean DEFAULT false NOT NULL, -- boolean 
  Attributes ByteA,
  AttributeSet_id varchar(255),
  CanDiscount boolean DEFAULT true NOT NULL, -- boolean
  Category varchar(255) NOT NULL,
  CatOrder integer,
  Code varchar(255), -- NOT NULL
  CodeType varchar(50),
  Discounted varchar(5) DEFAULT 'no',
  Display varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  IsCatalog bit, -- boolean
  IsCom boolean DEFAULT false, -- boolean 
  IsKitchen boolean DEFAULT false, -- boolean
  IsPack boolean DEFAULT false, -- boolean 
  IsScale boolean DEFAULT false, -- boolean 
  IsService boolean DEFAULT false, -- boolean 
  IsVerPAtrib boolean DEFAULT false, -- boolean
  IsVPrice boolean DEFAULT false, -- boolean 
  ManageStock boolean DEFAULT true, -- boolean
  Name varchar(255) NOT NULL,
  PackProduct varchar(255),
  PackQuantity float(52),
  PriceBuy float(52) DEFAULT 0 NOT NULL,
  PriceSell float(52) DEFAULT 0 NOT NULL,
  PrintKb boolean DEFAULT false NOT NULL, -- boolean
  PromotionId varchar(50),
  Reference varchar(255) NOT NULL,
  SendStatus boolean DEFAULT false NOT NULL, -- boolean
  StockCost float(52),
  StockUnits float(52) DEFAULT 0 NOT NULL,
  StockVolume float(52),
  TaxCat varchar(255) NOT NULL,
  TextTip varchar(255),
  warranty boolean DEFAULT false NOT NULL -- boolean
);


CREATE TABLE "Step04".Product_coms 
(
  Id varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  Product2 varchar(255) NOT NULL
);


CREATE TABLE "Step04".Product_kits 
(
  Id varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  Product_kit varchar(255) NOT NULL,
  Quantity float(52) NOT NULL
);


CREATE TABLE "Step04".Promotions 
(
  AllProducts boolean DEFAULT false, -- boolean
  Criteria ByteA,
  Id varchar(50) NOT NULL,
  IsEnabled boolean DEFAULT true, -- boolean
  Name varchar(255), -- NOT NULL
  Script ByteA NOT NULL
);


CREATE TABLE "Step04".Receipts 
(
  Attributes ByteA,
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Money varchar(255) NOT NULL,
  Person varchar(255)
);

 
CREATE TABLE "Step04".Roles 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Permissions ByteA,
  RightsLevel integer DEFAULT 4 NOT NULL
);


CREATE TABLE "Step04".Shift_Breaks 
(
  BreakId varchar(255) NOT NULL,
  EndTime timestamp,
  Id varchar(255) NOT NULL,
  ShiftId varchar(255) NOT NULL,
  StartTime timestamp NOT NULL
);


CREATE TABLE "Step04".Shifts 
(
  EndShift timestamp,
  Id varchar(255) NOT NULL,
  PplId varchar(255),
  StartShift timestamp NOT NULL
);


CREATE TABLE "Step04".Stock_Changes 
(
  BLObValue ByteA,
  Changes_processed integer,
  ChangeType integer,
  Display varchar(255),
  Field varchar(50),
  Id varchar(100) NOT NULL,
  Location varchar(255),
  ProductId varchar(255),
  TextValue varchar(255),
  UploadTime timestamp default current_timestamp,
  UserName varchar(50)
);


CREATE TABLE "Step04".Stock_Currents 
(
  AttributeSetInstance_Id varchar(255),
  Location varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  units float(52) DEFAULT 0 NOT NULL
);


CREATE TABLE "Step04".Stock_Diaries 
(
  AppUser varchar(255),
  AttributeSetInstance_Id varchar(255),
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Location varchar(255) NOT NULL,
  Price float(52) NOT NULL,
  Product varchar(255) NOT NULL,
  reason integer NOT NULL,
  units float(52) NOT NULL
);


CREATE TABLE "Step04".Stock_Levels 
(
  Id varchar(255) NOT NULL,
  Location varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  StockMaximum float(52) NOT NULL,
  StockSecurity float(52) NOT NULL
);


CREATE TABLE "Step04".Tax_Categories 
(
  Id varchar(255) NOT NULL,
  Name varchar(255) -- NOT NULL
);


CREATE TABLE "Step04".Taxes 
(
  Category varchar(255) NOT NULL,
  CustCategory varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  ParentId varchar(255),
  Rate float(52) DEFAULT 0 NOT NULL,
  RateCascade boolean DEFAULT false NOT NULL, -- boolean  
  RateOrder integer
);


CREATE TABLE "Step04".Tax_Items 
(
  Amount float(52) DEFAULT 0 NOT NULL,
  Base float(52) DEFAULT 0 NOT NULL,
  Id varchar(255) NOT NULL,
  Receipt varchar(255) NOT NULL,
  TaxId varchar(255) NOT NULL
);


CREATE TABLE "Step04".Third_Parties 
(
  Address varchar(255),
  CIF varchar(255) NOT NULL,
  ContactComm varchar(255),
  ContactFact varchar(255),
  Email varchar(255),
  FaxNumber varchar(255),
  Id varchar(255) NOT NULL,
  MobileNumber varchar(255),
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  PayRule varchar(255),
  PhoneNumber varchar(255),
  webpage varchar(255)
);


CREATE TABLE "Step04".Sales 
(
  Customer varchar(255),
  Id varchar(255) NOT NULL,
  Person varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean  
  SiteGUId varchar(50), -- NOT NULL
  Status integer DEFAULT 0 NOT NULL,
  TicketId integer NOT NULL,
  TicketType integer NOT NULL
);


CREATE TABLE "Step04".Sales_items 
(
  Attributes ByteA,
  AttributeSetInstance_Id varchar(255),
  Line integer NOT NULL,
  Price float(52) NOT NULL,
  Product varchar(255),
  RefundQty float(52) DEFAULT 0,
  TaxId varchar(255) NOT NULL,
  Ticket varchar(255) NOT NULL,
  units float(52) NOT NULL
);


CREATE TABLE "Step04".Vouchers 
(
  RedeemDate timestamp,
  RedeemTicketId varchar(50),
  SoldDate timestamp default current_timestamp,
  SoldTicketId varchar(50),
  Voucher varchar(50) NOT NULL
);
