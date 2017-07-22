CREATE TABLE breaks 
(
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  notes varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  visible boolean DEFAULT true NOT NULL
);


CREATE TABLE categories 
(
  CatOrder integer,
  CatShowName boolean DEFAULT true NOT NULL,
  colour varchar(50),
  id varchar(255) NOT NULL,
  image ByteA,
  name varchar(255) NOT NULL,
  ParentId varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TextTip varchar(255) DEFAULT NULL::varchar
);


CREATE TABLE ClosedCash 
(
  DateEnd timestamp without time zone,
  DateStart timestamp without time zone NOT NULL,
  host varchar(255) NOT NULL,
  HostSequence integer NOT NULL,
  money varchar(255) NOT NULL,
  NoSales integer DEFAULT 0 NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE csvimport 
(
  category varchar(255),
  code varchar(255),
  csverror varchar(255),
  id varchar(255) NOT NULL,
  name varchar(255),
  PreviousBuy double precision,
  PreviousSell double precision,
  PriceBuy double precision,
  PriceSell double precision,
  reference varchar(255),
  RowNumber varchar(255)
);


CREATE TABLE customers 
(
  address varchar(255),
  address2 varchar(255),
  card varchar(255),
  city varchar(255),
  country varchar(255),
  CurDate timestamp without time zone,
  CurDebt double precision DEFAULT 0,
  discount double precision DEFAULT 0,
  DOB timestamp without time zone,
  email varchar(255),
  fax varchar(255),
  FirstName varchar(255),
  id varchar(255) NOT NULL,
  image ByteA,
  LastName varchar(255),
  MaxDebt double precision DEFAULT 0 NOT NULL,
  name varchar(255) NOT NULL,
  notes varchar(255),
  phone varchar(255),
  phone2 varchar(255),
  postal varchar(255),
  region varchar(255),
  SearchKey varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TaxCategory varchar(255),
  TaxId varchar(255),
  visible boolean DEFAULT true NOT NULL
);


CREATE TABLE draweropened 
(
  id varchar(255) NOT NULL,
  name varchar(255),
  OpenDate timestamp without time zone DEFAULT now(),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TicketId varchar(255)
);


CREATE TABLE leaves 
(
  EndDate timestamp without time zone NOT NULL,
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  notes varchar(255),
  PplId varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  StartDate timestamp without time zone NOT NULL
);


CREATE TABLE LineRemoved 
(
  id varchar(255) NOT NULL,
  name varchar(255),
  ProductId varchar(255),
  ProductName varchar(255),
  RemovedDate timestamp without time zone DEFAULT now() NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TicketId varchar(255),
  units double precision NOT NULL
);


CREATE TABLE locations 
(
  address varchar(255),
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE merchants 
(
  address text NOT NULL,
  code varchar(8) NOT NULL,
  email varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  phone varchar(255) NOT NULL
);


CREATE TABLE orders 
(
  attributes varchar(255),
  auxiliary integer,
  details varchar(255),
  DisplayId integer,
  id varchar(50) NOT NULL,
  notes varchar(255),
  OrderId varchar(50),
  OrderTime timestamp without time zone DEFAULT now(),
  qty integer,
  sequence integer DEFAULT 0 NOT NULL,
  TicketId varchar(255)
);


CREATE TABLE payments 
(
  CardName varchar(255),
  id varchar(255) NOT NULL,
  notes varchar(255),
  payment varchar(255) NOT NULL,
  receipt varchar(255) NOT NULL,
  ReturnMsg ByteA,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  tendered double precision DEFAULT 0 NOT NULL,
  total double precision NOT NULL,
  transid varchar(255)
);


CREATE TABLE people 
(
  AppPassword varchar(255),
  card varchar(255),
  email varchar(255),
  id varchar(255) NOT NULL,
  image ByteA,
  name varchar(255),
  role varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  visible boolean NOT NULL
);


CREATE TABLE places 
(
  customer varchar(255),
  floor varchar(255) NOT NULL,
  id varchar(255) NOT NULL,
  locked boolean DEFAULT false,
  name varchar(255) NOT NULL,
  OpenedBy varchar(50),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TableMoved boolean DEFAULT false NOT NULL,
  TicketId varchar(255),
  waiter varchar(255),
  x integer NOT NULL,
  y integer NOT NULL
);


CREATE TABLE products 
(
  alias varchar(50),
  AllProducts boolean DEFAULT false,
  AlwaysAvailable boolean DEFAULT false NOT NULL,
  attributes ByteA,
  AttributeSet_id varchar(255),
  CanDiscount boolean DEFAULT true NOT NULL,
  category varchar(255) NOT NULL,
  CatOrder integer,
  code varchar(255) NOT NULL,
  CodeType varchar(50),
  discounted varchar(5) DEFAULT 'no'::varchar,
  display varchar(255),
  id varchar(255) NOT NULL,
  image ByteA,
  IsCatalog boolean,
  IsCom boolean DEFAULT false NOT NULL,
  IsKitchen boolean DEFAULT false NOT NULL,
  IsPack boolean DEFAULT false NOT NULL,
  IsScale boolean DEFAULT false NOT NULL,
  IsService boolean DEFAULT false NOT NULL,
  IsVerPAtrib boolean DEFAULT false NOT NULL,
  IsVPrice boolean DEFAULT false NOT NULL,
  ManageStock boolean DEFAULT true,
  name varchar(255) NOT NULL,
  PackProduct varchar(255),
  PackQuantity double precision,
  PriceBuy double precision DEFAULT 0 NOT NULL,
  PriceSell double precision DEFAULT 0 NOT NULL,
  PrintKb boolean DEFAULT false NOT NULL,
  PromotionId varchar(50),
  reference varchar(255) NOT NULL,
  SendStatus boolean DEFAULT false NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  StockCost double precision,
  StockUnits double precision DEFAULT 0 NOT NULL,
  StockVolume double precision,
  TaxCat varchar(255) NOT NULL,
  TextTip varchar(255),
  warranty boolean DEFAULT false NOT NULL
);


CREATE TABLE products_com 
(
  id varchar(255) NOT NULL,
  product varchar(255) NOT NULL,
  product2 varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE products_kit 
(
  id varchar(255) NOT NULL,
  product varchar(255) NOT NULL,
  product_kit varchar(255) NOT NULL,
  quantity double precision NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE promotions 
(
  AllProducts boolean DEFAULT false,
  criteria ByteA,
  id varchar(50) NOT NULL,
  IsEnabled boolean DEFAULT true,
  name varchar(255) NOT NULL,
  script ByteA NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE receipts 
(
  Attributes ByteA,
  DateNew timestamp without time zone NOT NULL,
  id varchar(255) NOT NULL,
  money varchar(255) NOT NULL,
  person varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE roles 
(
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  permissions ByteA,
  RightsLevel integer DEFAULT 4 NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE shift_breaks 
(
  BreakId varchar(255) NOT NULL,
  EndTime timestamp without time zone,
  id varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  ShiftId varchar(255) NOT NULL,
  SiteGUId varchar(50),
  StartTime timestamp without time zone NOT NULL
);


CREATE TABLE shifts 
(
  EndShift timestamp without time zone,
  id varchar(255) NOT NULL,
  PplId varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  StartShift timestamp without time zone NOT NULL
);


CREATE TABLE StockChanges 
(
  BLObValue ByteA,
  changes_processed integer,
  ChangeType integer,
  display varchar(255),
  field varchar(50),
  id varchar(100) NOT NULL,
  location varchar(255),
  ProductId varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TextValue varchar(255),
  UploadTime timestamp without time zone DEFAULT now(),
  username varchar(50)
);


CREATE TABLE StockCurrent 
(
  AttributeSetInstance_Id varchar(255),
  location varchar(255) NOT NULL,
  product varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  units double precision DEFAULT 0 NOT NULL
);


CREATE TABLE StockDiary 
(
  AppUser varchar(255),
  AttributeSetInstance_Id varchar(255),
  DateNew timestamp without time zone NOT NULL,
  id varchar(255) NOT NULL,
  location varchar(255) NOT NULL,
  price double precision NOT NULL,
  product varchar(255) NOT NULL,
  reason integer NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  units double precision NOT NULL
);


CREATE TABLE Stocklevel 
(
  id varchar(255) NOT NULL,
  location varchar(255) NOT NULL,
  product varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  Stockmaximum double precision NOT NULL,
  Stocksecurity double precision NOT NULL
);


CREATE TABLE taxcategories 
(
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE taxes 
(
  category varchar(255) NOT NULL,
  CustCategory varchar(255),
  id varchar(255) NOT NULL,
  name varchar(255) NOT NULL,
  ParentId varchar(255),
  rate double precision DEFAULT 0 NOT NULL,
  RateCascade boolean DEFAULT false NOT NULL,
  RateOrder integer,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50)
);


CREATE TABLE TaxLines 
(
  amount double precision DEFAULT 0 NOT NULL,
  base double precision DEFAULT 0 NOT NULL,
  id varchar(255) NOT NULL,
  receipt varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  taxid varchar(255) NOT NULL
);


CREATE TABLE ThirdParties 
(
  address varchar(255),
  cif varchar(255) NOT NULL,
  ContactComm varchar(255),
  ContactFact varchar(255),
  email varchar(255),
  FaxNumber varchar(255),
  id varchar(255) NOT NULL,
  MobileNumber varchar(255),
  name varchar(255) NOT NULL,
  notes varchar(255),
  PayRule varchar(255),
  PhoneNumber varchar(255),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  webpage varchar(255)
);


CREATE TABLE TicketLines 
(
  attributes ByteA,
  AttributeSetInstance_Id varchar(255),
  line integer NOT NULL,
  price double precision NOT NULL,
  product varchar(255),
  RefundQty double precision DEFAULT 0,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  TaxId varchar(255) NOT NULL,
  ticket varchar(255) NOT NULL,
  units double precision NOT NULL
);


CREATE TABLE tickets 
(
  customer varchar(255),
  id varchar(255) NOT NULL,
  person varchar(255) NOT NULL,
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  status integer DEFAULT 0 NOT NULL,
  TicketId integer NOT NULL,
  TicketType integer NOT NULL
);


CREATE TABLE vouchers 
(
  RedeemDate timestamp without time zone,
  RedeemTicketId varchar(50),
  SFlag boolean DEFAULT true,
  SiteGUId varchar(50),
  SoldDate timestamp without time zone DEFAULT now(),
  SoldTicketId varchar(50),
  voucher varchar(50) NOT NULL
);