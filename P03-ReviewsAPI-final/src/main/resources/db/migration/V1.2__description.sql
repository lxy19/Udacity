CREATE TABLE products(
  product_id int auto_increment,
  product_name varchar(255) not null,
  constraint product_pk PRIMARY KEY(product_id)
);
CREATE TABLE reviews(
  review_id int auto_increment,
  product_id int,
  review_description varchar(1000),
  constraint review_pk PRIMARY KEY (review_id),
  constraint review_fk FOREIGN KEY (product_id) REFERENCES products(product_id)
);
CREATE TABLE comments(
  comment_id int auto_increment,
  review_id int,
  content varchar(255),
  constraint comment_pk PRIMARY KEY(comment_id),
  constraint comment_fk FOREIGN KEY (review_id) references reviews(review_id)
);