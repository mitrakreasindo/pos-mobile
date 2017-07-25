-- In schema Step02; several constraints are removed
-- Several tables are already ignored in this version
-- Two tables are named Tickets and TicketLines
-- Bernaridho. 2017-07-25.

--CREATE TYPE timestamp FROM DateTime ;

CREATE TABLE "Step02".Breaks 
(
  "Id" varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  Visible boolean DEFAULT true NOT NULL -- boolean
);


CREATE TABLE "Step02".Categories 
(
  CatOrder integer,
  CatShowName boolean DEFAULT true NOT NULL, -- boolean
  Colour varchar(50),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255), -- NOT NULL
  ParentId varchar(255),
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TextTip varchar(255) DEFAULT NULL
);


CREATE TABLE "Step02".ClosedCash 
(
  DateEnd timestamp,
  DateStart timestamp NOT NULL,
  Host varchar(255) NOT NULL,
  HostSequence integer NOT NULL,
  Money varchar(255) NOT NULL,
  NoSales integer DEFAULT 0 NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".CSVImport 
(
  Category varchar(255),
  Code varchar(255),
  CSVError varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255),
  PreviousBuy float(53),
  PreviousSell float(53),
  PriceBuy float(53),
  PriceSell float(53),
  Reference varchar(255),
  RowNumber varchar(255)
);


CREATE TABLE "Step02".Customers 
(
  Address varchar(255),
  Address2 varchar(255),
  Card varchar(255),
  City varchar(255),
  Country varchar(255),
  CurDate timestamp,
  CurDebt float(53) DEFAULT 0,
  Discount float(53) DEFAULT 0,
  DOB timestamp,
  Email varchar(255),
  Fax varchar(255),
  FirstName varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  LastName varchar(255),
  MaxDebt float(53) DEFAULT 0, -- NOT NULL
  Name varchar(255) DEFAULT ' ' NOT NULL, -- fill value of this column with
  Notes varchar(255),
  Phone varchar(255),
  Phone2 varchar(255),
  Postal varchar(255),
  Region varchar(255),
  SearchKey varchar(255), -- NOT NULL
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TaxCategory varchar(255),
  TaxId varchar(255),
  Visible boolean DEFAULT true -- boolean NOT NULL
);


CREATE TABLE "Step02".DrawerOpened 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  OpenDate timestamp default current_timestamp,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TicketId varchar(255)
);


CREATE TABLE "Step02".Leaves 
(
  EndDate timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Notes varchar(255),
  PplId varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  StartDate timestamp NOT NULL
);


CREATE TABLE "Step02".LineRemoved 
(
  Id varchar(255) NOT NULL,
  Name varchar(255),
  ProductId varchar(255),
  ProductName varchar(255),
  RemovedDate timestamp default current_timestamp NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TicketId varchar(255),
  Units float(53) NOT NULL
);


CREATE TABLE "Step02".Locations 
(
  Address varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  SFlag boolean DEFAULT true, -- boolean 
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Merchants 
(
  Address text NOT NULL,
  Code varchar(8) NOT NULL,
  Email varchar(255) NOT NULL,
  Name varchar(255) NOT NULL,
  Phone varchar(255) NOT NULL
);


CREATE TABLE "Step02".Orders 
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


CREATE TABLE "Step02".Payments 
(
  CardName varchar(255),
  Id varchar(255) NOT NULL,
  Notes varchar(255),
  Payment varchar(255) NOT NULL,
  Receipt varchar(255) NOT NULL,
  ReturnMsg ByteA,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  Tendered float(53) DEFAULT 0 NOT NULL,
  Total float(53) NOT NULL,
  TransId varchar(255)
);


CREATE TABLE "Step02".People 
(
  AppPassword varchar(255),
  Card varchar(255),
  Email varchar(255),
  Id varchar(255) NOT NULL,
  Image ByteA,
  Name varchar(255),
  Role varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean 
  SiteGUId varchar(50), -- NOT NULL
  Visible boolean -- boolean NOT NULL
);


CREATE TABLE "Step02".Places 
(
  Customer varchar(255),
  floor varchar(255), -- NOT NULL
  Id varchar(255) NOT NULL,
  Locked boolean DEFAULT false, -- boolean 
  Name varchar(255), -- NOT NULL
  OpenedBy varchar(50),
  SFlag boolean DEFAULT true, -- boolean 
  SiteGUId varchar(50), -- NOT NULL
  TableMoved boolean DEFAULT false , -- boolean NOT NULL 
  TicketId varchar(255),
  waiter varchar(255),
  x integer NOT NULL,
  y integer NOT NULL
);


CREATE TABLE "Step02".Products 
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
  IsCatalog boolean, -- boolean
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
  PackQuantity float(53),
  PriceBuy float(53) DEFAULT 0 NOT NULL,
  PriceSell float(53) DEFAULT 0 NOT NULL,
  PrintKb boolean DEFAULT false NOT NULL, -- boolean
  PromotionId varchar(50),
  Reference varchar(255) NOT NULL,
  SendStatus boolean DEFAULT false NOT NULL, -- boolean
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  StockCost float(53),
  StockUnits float(53) DEFAULT 0 NOT NULL,
  StockVolume float(53),
  TaxCat varchar(255) NOT NULL,
  TextTip varchar(255),
  warranty boolean DEFAULT false NOT NULL -- boolean
);


CREATE TABLE "Step02".Products_com 
(
  Id varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  Product2 varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean 
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Products_kit 
(
  Id varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  Product_kit varchar(255) NOT NULL,
  Quantity float(53) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Promotions 
(
  AllProducts boolean DEFAULT false, -- boolean
  Criteria ByteA,
  Id varchar(50) NOT NULL,
  IsEnabled boolean DEFAULT true, -- boolean
  Name varchar(255), -- NOT NULL
  Script ByteA NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Receipts 
(
  Attributes ByteA,
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Money varchar(255) NOT NULL,
  Person varchar(255),
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);

 
CREATE TABLE "Step02".Roles 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  Permissions ByteA,
  RightsLevel integer DEFAULT 4 NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Shift_Breaks 
(
  BreakId varchar(255) NOT NULL,
  EndTime timestamp,
  Id varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  ShiftId varchar(255) NOT NULL,
  SiteGUId varchar(50), -- NOT NULL
  StartTime timestamp NOT NULL
);


CREATE TABLE "Step02".Shifts 
(
  EndShift timestamp,
  Id varchar(255) NOT NULL,
  PplId varchar(255),
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  StartShift timestamp NOT NULL
);


CREATE TABLE "Step02".StockChanges 
(
  BLObValue ByteA,
  Changes_processed integer,
  ChangeType integer,
  Display varchar(255),
  Field varchar(50),
  Id varchar(100) NOT NULL,
  Location varchar(255),
  ProductId varchar(255),
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TextValue varchar(255),
  UploadTime timestamp default current_timestamp,
  UserName varchar(50)
);


CREATE TABLE "Step02".StockCurrent 
(
  AttributeSetInstance_Id varchar(255),
  Location varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  units float(53) DEFAULT 0 NOT NULL
);


CREATE TABLE "Step02".StockDiary 
(
  AppUser varchar(255),
  AttributeSetInstance_Id varchar(255),
  DateNew timestamp NOT NULL,
  Id varchar(255) NOT NULL,
  Location varchar(255) NOT NULL,
  Price float(53) NOT NULL,
  Product varchar(255) NOT NULL,
  reason integer NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  units float(53) NOT NULL
);


CREATE TABLE "Step02".StockLevel 
(
  Id varchar(255) NOT NULL,
  Location varchar(255) NOT NULL,
  Product varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  StockMaximum float(53) NOT NULL,
  StockSecurity float(53) NOT NULL
);


CREATE TABLE "Step02".TaxCategories 
(
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".Taxes 
(
  Category varchar(255) NOT NULL,
  CustCategory varchar(255),
  Id varchar(255) NOT NULL,
  Name varchar(255), -- NOT NULL
  ParentId varchar(255),
  Rate float(53) DEFAULT 0 NOT NULL,
  RateCascade boolean DEFAULT false NOT NULL, -- boolean  
  RateOrder integer,
  SFlag boolean DEFAULT true, -- boolean  
  SiteGUId varchar(50) -- NOT NULL
);


CREATE TABLE "Step02".TaxLines 
(
  Amount float(53) DEFAULT 0 NOT NULL,
  Base float(53) DEFAULT 0 NOT NULL,
  Id varchar(255) NOT NULL,
  Receipt varchar(255) NOT NULL,
  SFlag boolean DEFAULT true, -- boolean
  SiteGUId varchar(50), -- NOT NULL
  TaxId varchar(255) NOT NULL
);


CREATE TABLE "Step02".ThirdParties 
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
  SFlag boolean DEFAULT true, -- boolean  
  SiteGUId varchar(50), -- NOT NULL
  webpage varchar(255)
);


CREATE TABLE "Step02".Sales 
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


CREATE TABLE "Step02".Sales_items 
(
  Attributes ByteA,
  AttributeSetInstance_Id varchar(255),
  Line integer NOT NULL,
  Price float(53) NOT NULL,
  Product varchar(255),
  RefundQty float(53) DEFAULT 0,
  SFlag boolean DEFAULT true, -- boolean  
  SiteGUId varchar(50), -- NOT NULL
  TaxId varchar(255) NOT NULL,
  Ticket varchar(255) NOT NULL,
  units float(53) NOT NULL
);


CREATE TABLE "Step02".Vouchers 
(
  RedeemDate timestamp,
  RedeemTicketId varchar(50),
  SFlag boolean DEFAULT true, -- boolean  
  SiteGUId varchar(50), -- NOT NULL
  SoldDate timestamp default current_timestamp,
  SoldTicketId varchar(50),
  Voucher varchar(50) NOT NULL
);
