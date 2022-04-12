--
-- Table structure for table 
--
DROP TABLE IF EXISTS task_forms_extend_mass_notification_cf;
CREATE TABLE task_forms_extend_mass_notification_cf(
  id_task INT NOT NULL,
  email_message LONG VARCHAR DEFAULT NULL,
  email_subject LONG VARCHAR DEFAULT NULL,
  email_sender_name VARCHAR(255) DEFAULT NULL,
  email_sender_email VARCHAR(255) DEFAULT NULL,
  email_extender_types VARCHAR(255) DEFAULT NULL,
  is_email_config SMALLINT DEFAULT 0 NOT NULL,
  dashboard_message LONG VARCHAR DEFAULT NULL,
  dashboard_subject LONG VARCHAR DEFAULT NULL,
  dashboard_sender VARCHAR(255) DEFAULT NULL,
  dashboard_extender_types VARCHAR(255) DEFAULT NULL,
  is_dashboard_config SMALLINT DEFAULT 0 NOT NULL,
  PRIMARY KEY (id_task)
);
