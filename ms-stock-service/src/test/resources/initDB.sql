create table if not exists stock_product (
  id int not null,
  stock_product_id int not null,
  name varchar(255),
  price int,
  category varchar(255),
  primary key (id)
);