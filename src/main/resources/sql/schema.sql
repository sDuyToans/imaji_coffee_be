CREATE TABLE products
(
    product_id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    name                VARCHAR(255)                          NOT NULL,
    description         TEXT,
    price               DECIMAL(10, 2)                        NOT NULL,
    old_price           DECIMAL(10, 2),
    is_available_at_web BOOLEAN     DEFAULT TRUE,
    quantity            INT         DEFAULT 0,
    created_at          TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by          VARCHAR(20)                           NOT NULL,
    updated_at          TIMESTAMP   DEFAULT NULL,
    updated_by          VARCHAR(20) DEFAULT NULL
);


CREATE TABLE product_images
(
    product_image_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    product_id       BIGINT                                NOT NULL,
    image_url        VARCHAR(500)                          NOT NULL,
    created_at       TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by       VARCHAR(20)                           NOT NULL,
    updated_at       TIMESTAMP   DEFAULT NULL,
    updated_by       VARCHAR(20) DEFAULT NULL,
    FOREIGN KEY (product_id) REFERENCES products (product_id) ON DELETE CASCADE
);

# export interface NewItem {
#   id: number;
# title: string;
# description: string;
# image: string;
# created_date: string;
# time: string;
# }

CREATE TABLE news
(
    new_id      BIGINT AUTO_INCREMENT PRIMARY KEY,
    title       VARCHAR(255)                          NOT NULL,
    description TEXT,
    image       VARCHAR(500)                          NOT NULL,
    created_at  TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by  VARCHAR(20)                           NOT NULL,
    updated_at  TIMESTAMP   DEFAULT NULL,
    updated_by  VARCHAR(20) DEFAULT NULL
);

CREATE TABLE event
(
    event_id   BIGINT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(255)                          NOT NULL,
    image      VARCHAR(500)                          NOT NULL,
    start_date TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    duration   TIME                                  NOT NULL,
    created_at TIMESTAMP   DEFAULT CURRENT_TIMESTAMP NOT NULL,
    created_by VARCHAR(20)                           NOT NULL,
    updated_at TIMESTAMP   DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL
)
