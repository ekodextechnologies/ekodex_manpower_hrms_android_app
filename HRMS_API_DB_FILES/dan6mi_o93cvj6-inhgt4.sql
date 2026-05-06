-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Generation Time: Aug 29, 2025 at 04:57 AM
-- Server version: 10.5.26-MariaDB
-- PHP Version: 8.3.15

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dan6mi_o93cvj6-inhgt4`
--

-- --------------------------------------------------------

--
-- Table structure for table `attendance`
--

CREATE TABLE `attendance` (
  `id` int(11) NOT NULL,
  `site_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `at_day` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `status` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `uniqueid` text DEFAULT NULL,
  `invoice_status` text DEFAULT 'Pending',
  `att_type` text DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` text DEFAULT NULL,
  `disabled_on` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `at_actual_charges`
--

CREATE TABLE `at_actual_charges` (
  `id` int(11) NOT NULL,
  `site_id` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `at_type` text DEFAULT NULL,
  `charges_type` text DEFAULT NULL,
  `total_nos` text DEFAULT NULL,
  `cost_per_head` text DEFAULT NULL,
  `total` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `billing_header_setting`
--

CREATE TABLE `billing_header_setting` (
  `id` int(11) NOT NULL,
  `item_desc` text DEFAULT NULL,
  `quantity` varchar(255) DEFAULT NULL,
  `rate` varchar(255) DEFAULT NULL,
  `total_amount` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_details`
--

CREATE TABLE `bill_details` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `rate_id` text DEFAULT NULL,
  `invoice_no` text DEFAULT NULL,
  `auto_no` text DEFAULT NULL,
  `invoice_month` text DEFAULT NULL,
  `invoice_year` text DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `total_nos` text DEFAULT NULL,
  `total_cost_without_gst` text DEFAULT NULL,
  `cgst` text DEFAULT NULL,
  `cgst_perc` text DEFAULT NULL,
  `sgst` text DEFAULT NULL,
  `sgst_perc` text DEFAULT NULL,
  `igst` text DEFAULT NULL,
  `igst_perc` text DEFAULT NULL,
  `final_amount` text DEFAULT NULL,
  `uniqueid` text DEFAULT NULL,
  `status` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `invoice_date` date DEFAULT NULL,
  `costperheadbill` varchar(20) DEFAULT '0',
  `invoice_from` text DEFAULT NULL,
  `invoice_to` text DEFAULT NULL,
  `billing_type` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `month_days` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `bill_supportings`
--

CREATE TABLE `bill_supportings` (
  `id` int(11) NOT NULL,
  `sp_image` text DEFAULT NULL,
  `sp_imagepath` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  `invoice_no` text DEFAULT NULL,
  `invoice_month` varchar(100) DEFAULT NULL,
  `invoice_year` varchar(100) DEFAULT NULL,
  `auto_no` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `branch`
--

CREATE TABLE `branch` (
  `id` int(11) NOT NULL,
  `branch_name` text DEFAULT NULL,
  `branch_code` text DEFAULT NULL,
  `company_name` text DEFAULT NULL,
  `email` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `pincode` varchar(11) DEFAULT NULL,
  `costing_method` text DEFAULT NULL,
  `def_purchase_ac` text DEFAULT NULL,
  `def_sales_ac` text DEFAULT NULL,
  `def_branch_recv_ac` text DEFAULT NULL,
  `def_branch_desp_ac` text DEFAULT NULL,
  `active` enum('0','1') DEFAULT '0',
  `created_by` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` int(11) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `phone` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `charges_master`
--

CREATE TABLE `charges_master` (
  `id` int(11) NOT NULL,
  `charges_master` text DEFAULT NULL,
  `label_display` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client_other_info`
--

CREATE TABLE `client_other_info` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `rate_id` text DEFAULT NULL,
  `emp_type` text DEFAULT NULL,
  `emp_hours` text DEFAULT NULL,
  `emp_nos` text DEFAULT NULL,
  `emp_cost_per_head` text DEFAULT NULL,
  `emp_total_cost` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `service_charges_type` text DEFAULT NULL,
  `service_charges` text DEFAULT NULL,
  `holiday_type` text DEFAULT NULL,
  `emp_perday_rate` text DEFAULT NULL,
  `divide_by` varchar(100) DEFAULT NULL,
  `emp_basic` text DEFAULT NULL,
  `emp_basicda` text DEFAULT NULL,
  `special_allow` text DEFAULT NULL,
  `other_allow` text DEFAULT NULL,
  `ot_rate` text DEFAULT NULL,
  `lww` text DEFAULT NULL,
  `bonus` text DEFAULT NULL,
  `fixed_leave_wages` text DEFAULT NULL,
  `fixed_uniform_wages` text DEFAULT NULL,
  `any_other` text DEFAULT NULL,
  `emp_da` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client_rates`
--

CREATE TABLE `client_rates` (
  `id` int(11) NOT NULL,
  `client_id` varchar(100) DEFAULT NULL,
  `cc_contact_person` text DEFAULT NULL,
  `cc_address` text DEFAULT NULL,
  `cc_contactno` text DEFAULT NULL,
  `cc_emailid` text DEFAULT NULL,
  `cc_city` text DEFAULT NULL,
  `cc_state` text DEFAULT NULL,
  `cc_country` text DEFAULT NULL,
  `cc_location_name` text DEFAULT NULL,
  `cc_loct_Startdate` text DEFAULT NULL,
  `cc_sales_person` text DEFAULT NULL,
  `cc_sales_person_email` text DEFAULT NULL,
  `cc_sales_person_phone` text DEFAULT NULL,
  `cc_bill_cycle_Date` text DEFAULT NULL,
  `cc_loct_status` varchar(100) DEFAULT NULL,
  `cc_expected_Date` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `disabled_by` text DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `branch_id` text DEFAULT NULL,
  `company_id` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `site_name` text DEFAULT NULL,
  `work_order_no` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `cc_cgst` text DEFAULT NULL,
  `cc_sgst` text DEFAULT NULL,
  `cc_igst` text DEFAULT NULL,
  `cc_est_billing_amount` text DEFAULT NULL,
  `display_ot` varchar(20) DEFAULT '0',
  `divide_by` varchar(20) DEFAULT NULL,
  `cc_billing_address` text DEFAULT NULL,
  `billing_nodays` text DEFAULT NULL,
  `attach_wages` varchar(20) DEFAULT '0',
  `convert_decimal` varchar(20) DEFAULT '0',
  `wages_header` text DEFAULT NULL,
  `multiply_ot_hours` varchar(20) DEFAULT '0',
  `calculate_uniform_days` varchar(20) DEFAULT '0',
  `calculate_earnedgross_withoutot` varchar(20) DEFAULT '0',
  `bill_without_rank` varchar(20) DEFAULT '0',
  `pf_wages_calculated_on` text DEFAULT NULL,
  `ESIC_wages_calculated_on` text DEFAULT NULL,
  `CTC_calculate` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `client_service_charges`
--

CREATE TABLE `client_service_charges` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `rate_id` text DEFAULT NULL,
  `type_of_services` text DEFAULT NULL,
  `count_services` text DEFAULT NULL,
  `charges_services` text DEFAULT NULL,
  `totalcharges_services` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `newemp_type` text DEFAULT NULL,
  `ea_calculateon` text DEFAULT NULL,
  `ea_operator` text DEFAULT NULL,
  `ea_comp_operator` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `companies`
--

CREATE TABLE `companies` (
  `id` int(11) NOT NULL,
  `company_name` text DEFAULT NULL,
  `email_id` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `phone` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `country` text DEFAULT NULL,
  `pincode` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `statecode` text DEFAULT NULL,
  `currency` text DEFAULT NULL,
  `service_tax_code` text DEFAULT NULL,
  `service_tax` text DEFAULT NULL,
  `contactable_person` text DEFAULT NULL,
  `pancard_no` varchar(50) DEFAULT NULL,
  `registration_no` varchar(20) DEFAULT NULL,
  `gst_no` varchar(20) DEFAULT NULL,
  `ac_no` varchar(17) DEFAULT NULL,
  `ac_type` text DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `ifsc_code` varchar(15) DEFAULT NULL,
  `micr_no` varchar(20) DEFAULT NULL,
  `branch_name` varchar(50) DEFAULT NULL,
  `active` enum('0','1') DEFAULT '0',
  `created_on` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `disabled_by` int(11) DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `company_id` text DEFAULT NULL,
  `branch_city` text DEFAULT NULL,
  `branch_country` text DEFAULT NULL,
  `branch_address` text DEFAULT NULL,
  `swift_ac_no` text DEFAULT NULL,
  `cheque_image` text DEFAULT NULL,
  `website` text DEFAULT NULL,
  `fin_from` text DEFAULT NULL,
  `fin_to` text DEFAULT NULL,
  `logo` text DEFAULT NULL,
  `cin_no` text DEFAULT NULL,
  `vat_tin` text DEFAULT NULL,
  `cst_tin` text DEFAULT NULL,
  `iec` text DEFAULT NULL,
  `terms` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `company_bank_detail`
--

CREATE TABLE `company_bank_detail` (
  `id` int(11) NOT NULL,
  `parent_id` text DEFAULT NULL,
  `ac_no` text DEFAULT NULL,
  `ac_type` text DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `ifsc_code` text DEFAULT NULL,
  `micr_no` text DEFAULT NULL,
  `branch_city` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `branch_address` text DEFAULT NULL,
  `swift_ac_no` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `debitnote_product_detail`
--

CREATE TABLE `debitnote_product_detail` (
  `payment_detail_id` int(11) NOT NULL,
  `bill_no` varchar(100) DEFAULT NULL,
  `product_name` text DEFAULT NULL,
  `product_code` text DEFAULT NULL,
  `price_per_unit` decimal(10,2) DEFAULT NULL,
  `quantity` decimal(10,2) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `productprice_afterdisc` decimal(10,2) NOT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `discount_perc` decimal(10,2) DEFAULT NULL,
  `taxable_value` decimal(10,2) DEFAULT NULL,
  `CGST` decimal(10,2) DEFAULT NULL,
  `cgstvalue` decimal(10,2) DEFAULT NULL,
  `SGST` decimal(10,2) DEFAULT NULL,
  `sgstvalue` decimal(10,2) DEFAULT NULL,
  `IGST` decimal(10,2) DEFAULT NULL,
  `igstvalue` decimal(10,2) DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL,
  `uid` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `debit_note`
--

CREATE TABLE `debit_note` (
  `payment_id` int(11) NOT NULL,
  `invoice_no` varchar(50) DEFAULT NULL,
  `invoice_date` date DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `state_code` varchar(20) DEFAULT NULL,
  `transportation_mode` varchar(40) DEFAULT NULL,
  `vehicle_no` varchar(40) DEFAULT NULL,
  `date_of_supply` text DEFAULT NULL,
  `place_of_supply` varchar(50) DEFAULT NULL,
  `receiver_name` varchar(30) DEFAULT NULL,
  `receiver_address` text DEFAULT NULL,
  `receiver_gstin` varchar(20) DEFAULT NULL,
  `receiver_state` varchar(30) DEFAULT NULL,
  `receiver_statecode` varchar(20) DEFAULT NULL,
  `consignee_name` varchar(40) DEFAULT NULL,
  `consignee_address` text DEFAULT NULL,
  `consignee_gstin` varchar(40) DEFAULT NULL,
  `consignee_state` varchar(40) DEFAULT NULL,
  `consignee_statecode` varchar(40) DEFAULT NULL,
  `amount_before_tax` decimal(10,2) DEFAULT NULL,
  `totalcgst` decimal(10,2) DEFAULT NULL,
  `totalsgst` decimal(10,2) DEFAULT NULL,
  `totaligst` decimal(10,2) DEFAULT NULL,
  `totalgst` decimal(10,2) DEFAULT NULL,
  `amount_after_tax` decimal(10,2) DEFAULT NULL,
  `balance_amount` decimal(10,2) DEFAULT NULL,
  `fr_amt` decimal(10,2) DEFAULT NULL,
  `fr_perc` decimal(10,0) DEFAULT NULL,
  `fr_charges` decimal(10,2) DEFAULT NULL,
  `hand_amt` decimal(10,2) DEFAULT NULL,
  `hand_perc` decimal(10,0) DEFAULT NULL,
  `hand_charges` decimal(10,2) DEFAULT NULL,
  `pck_amt` decimal(10,2) DEFAULT NULL,
  `pck_perc` decimal(10,0) DEFAULT NULL,
  `pck_charges` decimal(10,2) DEFAULT NULL,
  `fr_gst` decimal(10,2) DEFAULT NULL,
  `hand_gst` decimal(10,2) DEFAULT NULL,
  `pck_gst` decimal(10,2) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `debit_bill` varchar(50) DEFAULT NULL,
  `debit_date` text DEFAULT NULL,
  `dis_amt` decimal(10,2) DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE `employee` (
  `id` int(11) NOT NULL,
  `employee_code` int(255) DEFAULT NULL,
  `initial` varchar(100) DEFAULT NULL,
  `first_name` text DEFAULT NULL,
  `middle_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `dob` text DEFAULT NULL,
  `date_of_joining` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `pincode` varchar(100) DEFAULT NULL,
  `country` text DEFAULT NULL,
  `phone1` text DEFAULT NULL,
  `phone2` text DEFAULT NULL,
  `email_id` text DEFAULT NULL,
  `contact_person` text DEFAULT NULL,
  `contact_mobile` text DEFAULT NULL,
  `p_address` text DEFAULT NULL,
  `p_city` text DEFAULT NULL,
  `p_state` text DEFAULT NULL,
  `p_pincode` text DEFAULT NULL,
  `p_country` text DEFAULT NULL,
  `p_phone1` text DEFAULT NULL,
  `p_phone2` text DEFAULT NULL,
  `contact_relation` text DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `marital_status` text DEFAULT NULL,
  `mrg_date` text DEFAULT NULL,
  `cast` text DEFAULT NULL,
  `category` text DEFAULT NULL,
  `native_place` text DEFAULT NULL,
  `blood_group` text DEFAULT NULL,
  `driving_license` text DEFAULT NULL,
  `pancard_no` text DEFAULT NULL,
  `aadhar_no` text DEFAULT NULL,
  `passport_no` text DEFAULT NULL,
  `uan_no` text DEFAULT NULL,
  `passport_valid_date` text DEFAULT NULL,
  `lang1` varchar(100) DEFAULT NULL,
  `lang2` varchar(100) DEFAULT NULL,
  `lang3` varchar(100) DEFAULT NULL,
  `lang4` varchar(100) DEFAULT NULL,
  `lang5` varchar(100) DEFAULT NULL,
  `hobby1` varchar(100) DEFAULT NULL,
  `hobby2` varchar(100) DEFAULT NULL,
  `hobby3` varchar(100) DEFAULT NULL,
  `hobby4` varchar(100) DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `account_no` text DEFAULT NULL,
  `bank_address` text DEFAULT NULL,
  `bank_city` text DEFAULT NULL,
  `bank_state` text DEFAULT NULL,
  `bank_ifsc` text DEFAULT NULL,
  `bank_micr` text DEFAULT NULL,
  `cancelled_cheque_image` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(10) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(10) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `department` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `reporting_manager` text DEFAULT NULL,
  `reporting_user` text DEFAULT NULL,
  `separation_type` text DEFAULT NULL,
  `separate_reason` text DEFAULT NULL,
  `date_of_separation` text DEFAULT NULL,
  `notice_period` text DEFAULT NULL,
  `last_working_date_sp` text DEFAULT NULL,
  `handover_given_to` text DEFAULT NULL,
  `salary_template` text DEFAULT NULL,
  `gross_salary` varchar(11) DEFAULT NULL,
  `gross_salary_type` text DEFAULT NULL,
  `basic_salary` varchar(11) DEFAULT NULL,
  `basic_salary_type` text DEFAULT NULL,
  `sal_from_date` text DEFAULT NULL,
  `sal_to_date` text DEFAULT NULL,
  `pf_no` text DEFAULT NULL,
  `esis_no` text DEFAULT NULL,
  `pf_applicable` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `card_no` text DEFAULT NULL,
  `da_term` text DEFAULT NULL,
  `basic_term` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `em_status` text DEFAULT 'Working',
  `perday_salary` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `user_id` text DEFAULT NULL,
  `doc_name` text DEFAULT NULL,
  `client_code` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee_log`
--

CREATE TABLE `employee_log` (
  `id` int(11) NOT NULL,
  `employee_code` int(255) DEFAULT NULL,
  `initial` varchar(100) DEFAULT NULL,
  `first_name` text DEFAULT NULL,
  `middle_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `dob` text DEFAULT NULL,
  `date_of_joining` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `pincode` varchar(100) DEFAULT NULL,
  `country` text DEFAULT NULL,
  `phone1` text DEFAULT NULL,
  `phone2` text DEFAULT NULL,
  `email_id` text DEFAULT NULL,
  `contact_person` text DEFAULT NULL,
  `contact_mobile` text DEFAULT NULL,
  `p_address` text DEFAULT NULL,
  `p_city` text DEFAULT NULL,
  `p_state` text DEFAULT NULL,
  `p_pincode` text DEFAULT NULL,
  `p_country` text DEFAULT NULL,
  `p_phone1` text DEFAULT NULL,
  `p_phone2` text DEFAULT NULL,
  `contact_relation` text DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `marital_status` text DEFAULT NULL,
  `mrg_date` text DEFAULT NULL,
  `cast` text DEFAULT NULL,
  `category` text DEFAULT NULL,
  `native_place` text DEFAULT NULL,
  `blood_group` text DEFAULT NULL,
  `driving_license` text DEFAULT NULL,
  `pancard_no` text DEFAULT NULL,
  `aadhar_no` text DEFAULT NULL,
  `passport_no` text DEFAULT NULL,
  `uan_no` text DEFAULT NULL,
  `passport_valid_date` text DEFAULT NULL,
  `lang1` varchar(100) DEFAULT NULL,
  `lang2` varchar(100) DEFAULT NULL,
  `lang3` varchar(100) DEFAULT NULL,
  `lang4` varchar(100) DEFAULT NULL,
  `lang5` varchar(100) DEFAULT NULL,
  `hobby1` varchar(100) DEFAULT NULL,
  `hobby2` varchar(100) DEFAULT NULL,
  `hobby3` varchar(100) DEFAULT NULL,
  `hobby4` varchar(100) DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `account_no` text DEFAULT NULL,
  `bank_address` text DEFAULT NULL,
  `bank_city` text DEFAULT NULL,
  `bank_state` text DEFAULT NULL,
  `bank_ifsc` text DEFAULT NULL,
  `bank_micr` text DEFAULT NULL,
  `cancelled_cheque_image` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(10) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(10) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `department` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `reporting_manager` text DEFAULT NULL,
  `reporting_user` text DEFAULT NULL,
  `separation_type` text DEFAULT NULL,
  `separate_reason` text DEFAULT NULL,
  `date_of_separation` text DEFAULT NULL,
  `notice_period` text DEFAULT NULL,
  `last_working_date_sp` text DEFAULT NULL,
  `handover_given_to` text DEFAULT NULL,
  `salary_template` text DEFAULT NULL,
  `gross_salary` varchar(11) DEFAULT NULL,
  `gross_salary_type` text DEFAULT NULL,
  `basic_salary` varchar(11) DEFAULT NULL,
  `basic_salary_type` text DEFAULT NULL,
  `sal_from_date` text DEFAULT NULL,
  `sal_to_date` text DEFAULT NULL,
  `pf_no` text DEFAULT NULL,
  `esis_no` text DEFAULT NULL,
  `pf_applicable` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `card_no` text DEFAULT NULL,
  `da_term` text DEFAULT NULL,
  `basic_term` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `em_status` text DEFAULT 'Working',
  `perday_salary` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `user_id` text DEFAULT NULL,
  `doc_name` text DEFAULT NULL,
  `client_code` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `employee_test`
--

CREATE TABLE `employee_test` (
  `id` int(11) NOT NULL,
  `employee_code` int(255) DEFAULT NULL,
  `initial` varchar(100) DEFAULT NULL,
  `first_name` text DEFAULT NULL,
  `middle_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `dob` text DEFAULT NULL,
  `date_of_joining` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `pincode` varchar(100) DEFAULT NULL,
  `country` text DEFAULT NULL,
  `phone1` text DEFAULT NULL,
  `phone2` text DEFAULT NULL,
  `email_id` text DEFAULT NULL,
  `contact_person` text DEFAULT NULL,
  `contact_mobile` text DEFAULT NULL,
  `p_address` text DEFAULT NULL,
  `p_city` text DEFAULT NULL,
  `p_state` text DEFAULT NULL,
  `p_pincode` text DEFAULT NULL,
  `p_country` text DEFAULT NULL,
  `p_phone1` text DEFAULT NULL,
  `p_phone2` text DEFAULT NULL,
  `contact_relation` text DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `marital_status` text DEFAULT NULL,
  `mrg_date` text DEFAULT NULL,
  `cast` text DEFAULT NULL,
  `category` text DEFAULT NULL,
  `native_place` text DEFAULT NULL,
  `blood_group` text DEFAULT NULL,
  `driving_license` text DEFAULT NULL,
  `pancard_no` text DEFAULT NULL,
  `aadhar_no` text DEFAULT NULL,
  `passport_no` text DEFAULT NULL,
  `uan_no` text DEFAULT NULL,
  `passport_valid_date` text DEFAULT NULL,
  `lang1` varchar(100) DEFAULT NULL,
  `lang2` varchar(100) DEFAULT NULL,
  `lang3` varchar(100) DEFAULT NULL,
  `lang4` varchar(100) DEFAULT NULL,
  `lang5` varchar(100) DEFAULT NULL,
  `hobby1` varchar(100) DEFAULT NULL,
  `hobby2` varchar(100) DEFAULT NULL,
  `hobby3` varchar(100) DEFAULT NULL,
  `hobby4` varchar(100) DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `account_no` text DEFAULT NULL,
  `bank_address` text DEFAULT NULL,
  `bank_city` text DEFAULT NULL,
  `bank_state` text DEFAULT NULL,
  `bank_ifsc` text DEFAULT NULL,
  `bank_micr` text DEFAULT NULL,
  `cancelled_cheque_image` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(10) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(10) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `department` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `reporting_manager` text DEFAULT NULL,
  `reporting_user` text DEFAULT NULL,
  `separation_type` text DEFAULT NULL,
  `separate_reason` text DEFAULT NULL,
  `date_of_separation` text DEFAULT NULL,
  `notice_period` text DEFAULT NULL,
  `last_working_date_sp` text DEFAULT NULL,
  `handover_given_to` text DEFAULT NULL,
  `salary_template` text DEFAULT NULL,
  `gross_salary` varchar(11) DEFAULT NULL,
  `gross_salary_type` text DEFAULT NULL,
  `basic_salary` varchar(11) DEFAULT NULL,
  `basic_salary_type` text DEFAULT NULL,
  `sal_from_date` text DEFAULT NULL,
  `sal_to_date` text DEFAULT NULL,
  `pf_no` text DEFAULT NULL,
  `esis_no` text DEFAULT NULL,
  `pf_applicable` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `card_no` text DEFAULT NULL,
  `da_term` text DEFAULT NULL,
  `basic_term` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `em_status` text DEFAULT 'Working',
  `perday_salary` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `user_id` text DEFAULT NULL,
  `doc_name` text DEFAULT NULL,
  `client_code` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_advance`
--

CREATE TABLE `emp_advance` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `adv_date` date DEFAULT NULL,
  `payment_detail` text DEFAULT NULL,
  `amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `emp_id` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `particular` text DEFAULT NULL,
  `voucher_no` int(11) DEFAULT NULL,
  `cheque_no` text DEFAULT NULL,
  `credited_to` text DEFAULT NULL,
  `type` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `commission_paid` text DEFAULT NULL,
  `mode` text DEFAULT NULL,
  `status` text DEFAULT 'Pending',
  `approved_on` text DEFAULT NULL,
  `company_name` text DEFAULT NULL,
  `from_bank` text DEFAULT NULL,
  `to_bank` text DEFAULT NULL,
  `vendor_comm` text DEFAULT NULL,
  `site_name` text DEFAULT NULL,
  `client_name` text DEFAULT NULL,
  `xls_emp_name` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `payment_date` text DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `bank_ac_no` text DEFAULT NULL,
  `rejected_by` text DEFAULT NULL,
  `approved_by` text DEFAULT NULL,
  `proof_images` varchar(256) DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `ifsc_code` text DEFAULT NULL,
  `from_bank_acno` text DEFAULT NULL,
  `from_bank_ifsc` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_doc`
--

CREATE TABLE `emp_doc` (
  `id` int(11) NOT NULL,
  `emp_id` varchar(100) DEFAULT NULL,
  `doc_type` text DEFAULT NULL,
  `passing_year` text DEFAULT NULL,
  `doc_name` text DEFAULT NULL,
  `doc_status` text DEFAULT NULL,
  `doc_remark` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `doc_image` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_docs`
--

CREATE TABLE `emp_docs` (
  `id` int(11) NOT NULL,
  `emp_id` varchar(255) DEFAULT NULL,
  `doc_type` varchar(255) DEFAULT NULL,
  `doc_name` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `doc_remark` text DEFAULT NULL,
  `doc_status` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_experience`
--

CREATE TABLE `emp_experience` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `emp_company_name` text DEFAULT NULL,
  `emp_designation` text DEFAULT NULL,
  `emp_address` text DEFAULT NULL,
  `emp_city` text DEFAULT NULL,
  `emp_state` text DEFAULT NULL,
  `emp_country` text DEFAULT NULL,
  `emp_pincode` text DEFAULT NULL,
  `emp_joined_date` text DEFAULT NULL,
  `last_working_date` text DEFAULT NULL,
  `annual_ctc` text DEFAULT NULL,
  `monthly_ctc` text DEFAULT NULL,
  `reporting_to` text DEFAULT NULL,
  `reporting_to_designation` text DEFAULT NULL,
  `reporting_to_email` text DEFAULT NULL,
  `reporting_to_contact` text DEFAULT NULL,
  `gross_income` text DEFAULT NULL,
  `gross_tds` text DEFAULT NULL,
  `gross_pt` text DEFAULT NULL,
  `total_pt` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_nominee`
--

CREATE TABLE `emp_nominee` (
  `id` int(11) NOT NULL,
  `emp_id` text DEFAULT NULL,
  `f_initial` text DEFAULT NULL,
  `f_relative_name` text DEFAULT NULL,
  `f_gender` text DEFAULT NULL,
  `f_relation` text DEFAULT NULL,
  `f_dob` text DEFAULT NULL,
  `f_age` text DEFAULT NULL,
  `f_minor` varchar(50) DEFAULT NULL,
  `f_guardian` text DEFAULT NULL,
  `f_address` text DEFAULT NULL,
  `f_contact` text DEFAULT NULL,
  `f_email` text DEFAULT NULL,
  `f_sharepf` text DEFAULT NULL,
  `f_shareesic` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_salary_deduction`
--

CREATE TABLE `emp_salary_deduction` (
  `id` int(11) NOT NULL,
  `emp_id` int(11) DEFAULT NULL,
  `tmpl_id` text DEFAULT NULL,
  `deduction_name` text DEFAULT NULL,
  `deduction_rate` text DEFAULT NULL,
  `deduction_amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_salary_earning`
--

CREATE TABLE `emp_salary_earning` (
  `id` int(11) NOT NULL,
  `emp_id` text DEFAULT NULL,
  `tmpl_id` text DEFAULT NULL,
  `earning_name` text DEFAULT NULL,
  `earning_rate` text DEFAULT NULL,
  `earning_amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_type`
--

CREATE TABLE `emp_type` (
  `id` int(11) NOT NULL,
  `emp_type` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `emp_wages`
--

CREATE TABLE `emp_wages` (
  `id` int(11) NOT NULL,
  `site_id` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `client_name` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `at_month` varchar(20) DEFAULT NULL,
  `at_year` varchar(50) DEFAULT NULL,
  `at_type` varchar(50) DEFAULT NULL,
  `emp_id` varchar(100) DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `basic` decimal(10,2) DEFAULT 0.00,
  `vda` decimal(10,2) DEFAULT 0.00,
  `basic_vda` decimal(10,2) DEFAULT 0.00,
  `special_allow` decimal(10,2) DEFAULT 0.00,
  `hra` decimal(10,2) DEFAULT 0.00,
  `other_allow` decimal(10,2) DEFAULT 0.00,
  `lww` decimal(10,2) DEFAULT 0.00,
  `bonus` decimal(10,2) DEFAULT 0.00,
  `gross` decimal(10,2) DEFAULT 0.00,
  `ot_rate` decimal(10,2) DEFAULT 0.00,
  `fixed_leave_wages` text DEFAULT NULL,
  `per_day_rate` text DEFAULT NULL,
  `fixed_uniform_wages` text DEFAULT NULL,
  `month_days` varchar(50) DEFAULT NULL,
  `present_days` varchar(50) DEFAULT NULL,
  `paid_holiday` varchar(50) DEFAULT NULL,
  `net_days` varchar(100) DEFAULT NULL,
  `ot_days` varchar(100) DEFAULT NULL,
  `earned_basic` decimal(10,2) DEFAULT 0.00,
  `earned_da` decimal(10,2) DEFAULT 0.00,
  `earned_basic_da` decimal(10,2) DEFAULT 0.00,
  `earned_special_allow` decimal(10,2) DEFAULT 0.00,
  `earned_hra` decimal(10,2) DEFAULT 0.00,
  `earned_other_allow` decimal(10,2) DEFAULT 0.00,
  `earned_lww` decimal(10,2) DEFAULT 0.00,
  `earned_bonus` decimal(10,2) DEFAULT 0.00,
  `earned_ot_wages` decimal(10,2) DEFAULT 0.00,
  `earned_gross` decimal(10,2) DEFAULT 0.00,
  `pf_wages` decimal(10,2) DEFAULT 0.00,
  `esic_wages` decimal(10,2) DEFAULT 0.00,
  `deduct_pf` decimal(10,2) DEFAULT 0.00,
  `deduct_pt` decimal(10,2) DEFAULT 0.00,
  `deduct_esic` decimal(10,2) DEFAULT 0.00,
  `deduct_lwf` decimal(10,2) DEFAULT 0.00,
  `deduct_other` decimal(10,2) DEFAULT 0.00,
  `deduct_advance` decimal(10,2) DEFAULT 0.00,
  `deduct_loan` text DEFAULT NULL,
  `deduct_uniform` text DEFAULT NULL,
  `deduct_id` text DEFAULT NULL,
  `deduct_recovery` text DEFAULT NULL,
  `deduct_installment` text DEFAULT NULL,
  `total_deduction` decimal(10,2) DEFAULT 0.00,
  `net_take_home` decimal(10,2) DEFAULT 0.00,
  `arrears_payable` text DEFAULT NULL,
  `salary_payable` text DEFAULT NULL,
  `emp_pf` decimal(10,2) DEFAULT 0.00,
  `emp_esic` decimal(10,2) DEFAULT 0.00,
  `emp_lwf` decimal(10,2) DEFAULT 0.00,
  `emp_gratuty` decimal(10,2) DEFAULT 0.00,
  `emp_ctc` decimal(10,2) DEFAULT 0.00,
  `emp_service_charges` decimal(10,2) DEFAULT 0.00,
  `emp_ictc` decimal(10,2) DEFAULT 0.00,
  `emp_gst` decimal(10,2) DEFAULT 0.00,
  `emp_invoice_amount` decimal(10,2) DEFAULT 0.00,
  `billing_type` text DEFAULT NULL,
  `total_nos` text DEFAULT NULL,
  `absent_days` text DEFAULT NULL,
  `weekly_off` text DEFAULT NULL,
  `paid_leaves` text DEFAULT NULL,
  `half_day` text DEFAULT NULL,
  `any_other` text DEFAULT NULL,
  `earned_leave_wages` text DEFAULT NULL,
  `earned_uniform_wages` text DEFAULT NULL,
  `earned_any_other` text DEFAULT NULL,
  `CGST` text DEFAULT NULL,
  `SGST` text DEFAULT NULL,
  `IGST` text DEFAULT NULL,
  `emp_any_other` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `equipment_master`
--

CREATE TABLE `equipment_master` (
  `id` int(11) NOT NULL,
  `equipment_master` text DEFAULT NULL,
  `rate` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `excel_headers`
--

CREATE TABLE `excel_headers` (
  `id` int(11) NOT NULL,
  `wg_original_header` varchar(255) DEFAULT NULL,
  `wg_cleaned_header` varchar(255) DEFAULT NULL,
  `wg_table_name` varchar(255) DEFAULT NULL,
  `created_at` varchar(100) DEFAULT NULL,
  `client_id` varchar(11) DEFAULT NULL,
  `site_id` varchar(11) DEFAULT NULL,
  `search_from_month` varchar(20) DEFAULT NULL,
  `search_from_year` varchar(20) DEFAULT NULL,
  `wg_filename` varchar(255) DEFAULT NULL,
  `approved` varchar(20) DEFAULT '0',
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `disabled_by` varchar(50) DEFAULT NULL,
  `disabled_on` varchar(100) DEFAULT NULL,
  `modified_by` varchar(50) DEFAULT NULL,
  `modified_on` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `gang_master`
--

CREATE TABLE `gang_master` (
  `id` int(11) NOT NULL,
  `gang_master` text DEFAULT NULL,
  `contact_no` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `branch_id` varchar(20) DEFAULT NULL,
  `company_id` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `holidays`
--

CREATE TABLE `holidays` (
  `id` int(11) NOT NULL,
  `state` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `branch_id` text DEFAULT NULL,
  `company_id` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `holiday_detail`
--

CREATE TABLE `holiday_detail` (
  `id` int(11) NOT NULL,
  `holiday_id` varchar(20) DEFAULT NULL,
  `holiday_name` text DEFAULT NULL,
  `holiday_date` text DEFAULT NULL,
  `image` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `lables`
--

CREATE TABLE `lables` (
  `id` int(11) NOT NULL,
  `lable_name` text DEFAULT NULL,
  `lable_type` text DEFAULT NULL,
  `lable_value` text DEFAULT NULL,
  `active` enum('0','1') DEFAULT '0',
  `created_by` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` int(11) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `manpower_attendance`
--

CREATE TABLE `manpower_attendance` (
  `id` int(11) NOT NULL,
  `ma_date` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `at_day` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `shift` text DEFAULT NULL,
  `in_time` text DEFAULT NULL,
  `out_time` text DEFAULT NULL,
  `total_hours` text DEFAULT NULL,
  `rate` text DEFAULT NULL,
  `count` text DEFAULT NULL,
  `supervisior` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `final_amount` text DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `modified_on` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `disabled_on` varchar(255) DEFAULT NULL,
  `disabled_by` varchar(255) DEFAULT NULL,
  `active` tinyint(1) DEFAULT 0,
  `remark` text DEFAULT NULL,
  `at_type` text DEFAULT NULL,
  `invoice_status` text DEFAULT 'Pending',
  `ot_final_amount` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `modules`
--

CREATE TABLE `modules` (
  `module_id` int(11) NOT NULL,
  `segment` varchar(100) DEFAULT NULL,
  `main_module` varchar(100) DEFAULT NULL,
  `module_name` varchar(100) DEFAULT NULL,
  `module_url` varchar(100) DEFAULT NULL,
  `module_icon` text DEFAULT NULL,
  `title_alias` text DEFAULT NULL,
  `sort_order` int(11) NOT NULL,
  `active` enum('0','1') DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `new_client`
--

CREATE TABLE `new_client` (
  `id` int(11) NOT NULL,
  `company_name` text DEFAULT NULL,
  `contact_no` text DEFAULT NULL,
  `client_email` text DEFAULT NULL,
  `contact_person` text DEFAULT NULL,
  `client_add` text DEFAULT NULL,
  `client_city` text DEFAULT NULL,
  `client_state` text DEFAULT NULL,
  `city_pincode` text DEFAULT NULL,
  `client_gst_state_code` text DEFAULT NULL,
  `client_gst_no` text DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `client_acc_no` text DEFAULT NULL,
  `ifsc_code` text DEFAULT NULL,
  `bank_branch` text DEFAULT NULL,
  `bank_city` text DEFAULT NULL,
  `micr_code` text DEFAULT NULL,
  `active` varchar(10) NOT NULL DEFAULT '0',
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `modified_by` varchar(10) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_by` text DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `sac_code` text DEFAULT NULL,
  `other_info` text DEFAULT NULL,
  `terms` text DEFAULT NULL,
  `company_bank_name` text DEFAULT NULL,
  `billing_company` text DEFAULT NULL,
  `billing_company_id` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `orders`
--

CREATE TABLE `orders` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `unit_id` text DEFAULT NULL,
  `type` text DEFAULT NULL,
  `invoice_month` text DEFAULT NULL,
  `invoice_year` text DEFAULT NULL,
  `total_nos` text DEFAULT NULL,
  `cost_per_head` text DEFAULT NULL,
  `status` text DEFAULT NULL,
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `invoice_date` text DEFAULT NULL,
  `uniqueid` text DEFAULT NULL,
  `service_charges_type` text DEFAULT NULL,
  `service_charges` text DEFAULT NULL,
  `total` text DEFAULT NULL,
  `invoice_no` text DEFAULT NULL,
  `auto_no` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `no_of_days` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `sac_code` text DEFAULT NULL,
  `tb_date` text DEFAULT NULL,
  `tb_from` text DEFAULT NULL,
  `tb_to` text DEFAULT NULL,
  `lr_no` text DEFAULT NULL,
  `tb_vehicle_no` text DEFAULT NULL,
  `tb_weight` text DEFAULT NULL,
  `tb_invoice_no` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payment_history`
--

CREATE TABLE `payment_history` (
  `payment_historyid` int(11) NOT NULL,
  `bill_no` varchar(20) DEFAULT NULL,
  `paid_amount` decimal(10,2) DEFAULT NULL,
  `tax_type` varchar(100) DEFAULT NULL,
  `tax_percent` decimal(10,2) DEFAULT NULL,
  `tax_paid` decimal(10,2) DEFAULT NULL,
  `transaction_type` varchar(20) DEFAULT NULL,
  `transaction_id` varchar(50) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `uid` text DEFAULT NULL,
  `payment_date` text DEFAULT NULL,
  `tds` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `payslip_settings`
--

CREATE TABLE `payslip_settings` (
  `id` int(11) NOT NULL,
  `client_id` varchar(20) DEFAULT NULL,
  `site_id` varchar(20) DEFAULT NULL,
  `employee_name_header` varchar(255) DEFAULT NULL,
  `employee_code_header` varchar(255) DEFAULT NULL,
  `site_name_header` varchar(255) DEFAULT NULL,
  `designation_header` varchar(255) DEFAULT NULL,
  `doj_header` varchar(255) DEFAULT NULL,
  `uan_header` varchar(255) DEFAULT NULL,
  `month_days_header` varchar(255) DEFAULT NULL,
  `esic_ip_header` varchar(255) DEFAULT NULL,
  `payable_days_header` varchar(255) DEFAULT NULL,
  `dob_header` varchar(255) DEFAULT NULL,
  `pan_header` varchar(255) DEFAULT NULL,
  `work_days_header` varchar(255) DEFAULT NULL,
  `aadhar_no_header` varchar(255) DEFAULT NULL,
  `weekly_offs_header` varchar(255) DEFAULT NULL,
  `bank_name_header` varchar(255) DEFAULT NULL,
  `paid_hol_header` varchar(255) DEFAULT NULL,
  `account_no_header` varchar(255) DEFAULT NULL,
  `paid_leave_header` varchar(255) DEFAULT NULL,
  `ifsc_code_header` varchar(255) DEFAULT NULL,
  `absent_days_header` varchar(255) DEFAULT NULL,
  `insurance_no_header` varchar(255) DEFAULT NULL,
  `basic_vda_header` varchar(255) DEFAULT NULL,
  `earned_basic_da_header` varchar(255) DEFAULT NULL,
  `deduct_pf_header` varchar(255) DEFAULT NULL,
  `special_allow_header` varchar(255) DEFAULT NULL,
  `earned_special_allow_header` varchar(255) DEFAULT NULL,
  `deduct_esic_header` varchar(255) DEFAULT NULL,
  `hra_header` varchar(255) DEFAULT NULL,
  `earned_hra_header` varchar(255) DEFAULT NULL,
  `deduct_pt_header` varchar(255) DEFAULT NULL,
  `other_allow_header` varchar(255) DEFAULT NULL,
  `earned_other_allow_header` varchar(255) DEFAULT NULL,
  `deduct_lwf_header` varchar(255) DEFAULT NULL,
  `lww_header` varchar(255) DEFAULT NULL,
  `earned_lww_header` varchar(255) DEFAULT NULL,
  `deduct_uniform_header` varchar(255) DEFAULT NULL,
  `bonus_header` varchar(255) DEFAULT NULL,
  `earned_bonus_header` varchar(255) DEFAULT NULL,
  `deduct_advance_header` varchar(255) DEFAULT NULL,
  `ot_rate_header` varchar(255) DEFAULT NULL,
  `earned_ot_wages_header` varchar(255) DEFAULT NULL,
  `total_deduction_header` varchar(255) DEFAULT NULL,
  `gross_header` varchar(255) DEFAULT NULL,
  `earned_gross_header` varchar(255) DEFAULT NULL,
  `net_take_home_header` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `product_gallery`
--

CREATE TABLE `product_gallery` (
  `id` int(11) NOT NULL,
  `image` text NOT NULL,
  `imagepath` text DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `branch_id` int(11) DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(50) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `purchase_bill`
--

CREATE TABLE `purchase_bill` (
  `payment_id` int(11) NOT NULL,
  `invoice_no` varchar(50) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `invoice_date` date DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `state_code` varchar(20) DEFAULT NULL,
  `transportation_mode` varchar(40) DEFAULT NULL,
  `vehicle_no` varchar(40) DEFAULT NULL,
  `date_of_supply` text DEFAULT NULL,
  `place_of_supply` varchar(50) DEFAULT NULL,
  `receiver_name` varchar(30) DEFAULT NULL,
  `receiver_address` text DEFAULT NULL,
  `receiver_gstin` varchar(20) DEFAULT NULL,
  `receiver_state` varchar(30) DEFAULT NULL,
  `receiver_statecode` varchar(20) DEFAULT NULL,
  `consignee_name` varchar(40) DEFAULT NULL,
  `consignee_address` text DEFAULT NULL,
  `consignee_gstin` varchar(40) DEFAULT NULL,
  `consignee_state` varchar(40) DEFAULT NULL,
  `consignee_statecode` varchar(40) DEFAULT NULL,
  `amount_before_tax` decimal(10,2) DEFAULT NULL,
  `totalcgst` decimal(10,2) DEFAULT NULL,
  `totalsgst` decimal(10,2) DEFAULT NULL,
  `totaligst` decimal(10,2) DEFAULT NULL,
  `totalgst` decimal(10,2) DEFAULT NULL,
  `amount_after_tax` decimal(10,2) DEFAULT NULL,
  `balance_amount` decimal(10,2) DEFAULT NULL,
  `fr_amt` decimal(10,2) NOT NULL,
  `fr_perc` decimal(10,0) NOT NULL,
  `fr_charges` decimal(10,2) NOT NULL,
  `hand_amt` decimal(10,2) NOT NULL,
  `hand_perc` decimal(10,0) NOT NULL,
  `hand_charges` decimal(10,2) NOT NULL,
  `pck_amt` decimal(10,2) NOT NULL,
  `pck_perc` decimal(10,0) NOT NULL,
  `pck_charges` decimal(10,2) NOT NULL,
  `fr_gst` decimal(10,2) NOT NULL,
  `hand_gst` decimal(10,2) NOT NULL,
  `pck_gst` decimal(10,2) NOT NULL,
  `created_on` datetime DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `dis_amt` text DEFAULT NULL,
  `created_by` varchar(30) DEFAULT NULL,
  `disabled_by` varchar(30) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `purchase_bill_history`
--

CREATE TABLE `purchase_bill_history` (
  `payment_historyid` int(11) NOT NULL,
  `bill_no` varchar(20) DEFAULT NULL,
  `paid_amount` decimal(10,2) DEFAULT NULL,
  `tax_type` varchar(100) DEFAULT NULL,
  `tax_percent` decimal(10,2) DEFAULT NULL,
  `tax_paid` decimal(10,2) DEFAULT NULL,
  `transaction_type` varchar(20) DEFAULT NULL,
  `transaction_id` varchar(50) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `uid` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `purchase_bill_product_detail`
--

CREATE TABLE `purchase_bill_product_detail` (
  `payment_detail_id` int(11) NOT NULL,
  `bill_no` varchar(100) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `product_name` text DEFAULT NULL,
  `product_code` text DEFAULT NULL,
  `price_per_unit` decimal(10,2) DEFAULT NULL,
  `quantity` decimal(10,2) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `productprice_afterdisc` decimal(10,2) NOT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `discount_perc` decimal(10,2) DEFAULT NULL,
  `taxable_value` decimal(10,2) DEFAULT NULL,
  `CGST` decimal(10,2) DEFAULT NULL,
  `cgstvalue` decimal(10,2) DEFAULT NULL,
  `SGST` decimal(10,2) DEFAULT NULL,
  `sgstvalue` decimal(10,2) DEFAULT NULL,
  `IGST` decimal(10,2) DEFAULT NULL,
  `igstvalue` decimal(10,2) DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `raise_ticket`
--

CREATE TABLE `raise_ticket` (
  `id` int(11) NOT NULL,
  `ticketid` text NOT NULL,
  `raised_by` text DEFAULT NULL,
  `ticket_detail` text NOT NULL,
  `docket_no` text DEFAULT NULL,
  `image` text DEFAULT NULL,
  `video` text DEFAULT NULL,
  `subject` text DEFAULT NULL,
  `status` text NOT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_on` timestamp NOT NULL DEFAULT current_timestamp(),
  `modified_by` int(11) DEFAULT NULL,
  `modified_on` timestamp NOT NULL DEFAULT current_timestamp() ON UPDATE current_timestamp(),
  `disabled_by` int(11) DEFAULT NULL,
  `disabled_on` timestamp NOT NULL DEFAULT '0000-00-00 00:00:00',
  `active` enum('0','1') DEFAULT '0',
  `lock_screen` enum('N','Y') DEFAULT 'N'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `rate_charts`
--

CREATE TABLE `rate_charts` (
  `id` int(11) NOT NULL,
  `client_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `client_name` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `site_name` text DEFAULT NULL,
  `wo_type` text DEFAULT NULL,
  `size` text DEFAULT NULL,
  `from_wt` text DEFAULT NULL,
  `to_wt` text DEFAULT NULL,
  `type` text DEFAULT NULL,
  `exam_percentage` text DEFAULT NULL,
  `rate` text DEFAULT NULL,
  `created_by` text DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `disabled_by` text DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `active` varchar(20) NOT NULL DEFAULT '0',
  `equi_type` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salary_deduction`
--

CREATE TABLE `salary_deduction` (
  `id` int(11) NOT NULL,
  `tmpl_id` text DEFAULT NULL,
  `deduction_name` text DEFAULT NULL,
  `deduction_rate` text DEFAULT NULL,
  `deduction_amount` text DEFAULT NULL,
  `from_date` text DEFAULT NULL,
  `to_date` text DEFAULT NULL,
  `dd_calculateon` text DEFAULT NULL,
  `dd_operator` varchar(100) DEFAULT NULL,
  `dd_comp_operator` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salary_earning`
--

CREATE TABLE `salary_earning` (
  `id` int(11) NOT NULL,
  `tmpl_id` text DEFAULT NULL,
  `earning_name` text DEFAULT NULL,
  `earning_rate` text DEFAULT NULL,
  `earning_amount` text DEFAULT NULL,
  `ea_from_date` varchar(200) DEFAULT NULL,
  `ea_to_date` varchar(200) DEFAULT NULL,
  `ea_calculateon` text DEFAULT NULL,
  `ea_operator` varchar(100) DEFAULT NULL,
  `ea_comp_operator` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salary_slip`
--

CREATE TABLE `salary_slip` (
  `id` int(11) NOT NULL,
  `emp_id` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `salary_template` text DEFAULT NULL,
  `def_gross_salary` text DEFAULT NULL,
  `def_gross_salary_type` text DEFAULT NULL,
  `def_basic_salary` text DEFAULT NULL,
  `def_basic_salary_type` text DEFAULT NULL,
  `basic_rate` text DEFAULT NULL,
  `no_of_days` text DEFAULT NULL,
  `total_earning` text DEFAULT NULL,
  `total_deduction` text DEFAULT NULL,
  `active` varchar(20) NOT NULL DEFAULT '0',
  `site_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `gross_salary` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `card_no` text DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `remark` text DEFAULT NULL,
  `hold_salary` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `salary_template`
--

CREATE TABLE `salary_template` (
  `id` int(11) NOT NULL,
  `template_name` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `gross_salary` text DEFAULT NULL,
  `gross_salary_type` text DEFAULT NULL,
  `basic_salary` text DEFAULT NULL,
  `basic_salary_type` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(20) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(20) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(20) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `company_id` text DEFAULT NULL,
  `branch_id` text DEFAULT NULL,
  `perday_salary` text DEFAULT NULL,
  `hra` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sale_bill`
--

CREATE TABLE `sale_bill` (
  `payment_id` int(11) NOT NULL,
  `invoice_no` varchar(50) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `invoice_date` date DEFAULT NULL,
  `state` varchar(30) DEFAULT NULL,
  `state_code` varchar(20) DEFAULT NULL,
  `transportation_mode` varchar(40) DEFAULT NULL,
  `vehicle_no` varchar(40) DEFAULT NULL,
  `date_of_supply` text DEFAULT NULL,
  `place_of_supply` varchar(50) DEFAULT NULL,
  `receiver_name` varchar(30) DEFAULT NULL,
  `receiver_address` text DEFAULT NULL,
  `receiver_gstin` varchar(20) DEFAULT NULL,
  `receiver_state` varchar(30) DEFAULT NULL,
  `receiver_statecode` varchar(20) DEFAULT NULL,
  `consignee_name` varchar(40) DEFAULT NULL,
  `consignee_address` text DEFAULT NULL,
  `consignee_gstin` varchar(40) DEFAULT NULL,
  `consignee_state` varchar(40) DEFAULT NULL,
  `consignee_statecode` varchar(40) DEFAULT NULL,
  `amount_before_tax` decimal(10,2) DEFAULT NULL,
  `totalcgst` decimal(10,2) DEFAULT NULL,
  `totalsgst` decimal(10,2) DEFAULT NULL,
  `totaligst` decimal(10,2) DEFAULT NULL,
  `totalgst` decimal(10,2) DEFAULT NULL,
  `amount_after_tax` decimal(10,2) DEFAULT NULL,
  `balance_amount` decimal(10,2) DEFAULT NULL,
  `fr_amt` decimal(10,2) NOT NULL,
  `fr_perc` decimal(10,0) NOT NULL,
  `fr_charges` decimal(10,2) NOT NULL,
  `hand_amt` decimal(10,2) NOT NULL,
  `hand_perc` decimal(10,0) NOT NULL,
  `hand_charges` decimal(10,2) NOT NULL,
  `pck_amt` decimal(10,2) NOT NULL,
  `pck_perc` decimal(10,0) NOT NULL,
  `pck_charges` decimal(10,2) NOT NULL,
  `fr_gst` decimal(10,2) NOT NULL,
  `hand_gst` decimal(10,2) NOT NULL,
  `pck_gst` decimal(10,2) NOT NULL,
  `dis_amt` text DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `received_amt` text DEFAULT NULL,
  `received_date` text DEFAULT NULL,
  `description` text NOT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(30) DEFAULT NULL,
  `disabled_by` varchar(30) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sale_bill_history`
--

CREATE TABLE `sale_bill_history` (
  `payment_historyid` int(11) NOT NULL,
  `bill_no` varchar(20) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `paid_amount` decimal(10,2) DEFAULT NULL,
  `tax_type` varchar(100) DEFAULT NULL,
  `tax_percent` decimal(10,2) DEFAULT NULL,
  `tax_paid` decimal(10,2) DEFAULT NULL,
  `transaction_type` varchar(20) DEFAULT NULL,
  `transaction_id` varchar(50) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `sale_bill_product_detail`
--

CREATE TABLE `sale_bill_product_detail` (
  `payment_detail_id` int(11) NOT NULL,
  `bill_no` varchar(100) DEFAULT NULL,
  `uid` int(11) NOT NULL,
  `product_name` text DEFAULT NULL,
  `product_code` text DEFAULT NULL,
  `price_per_unit` decimal(10,2) DEFAULT NULL,
  `quantity` decimal(10,2) DEFAULT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  `productprice_afterdisc` decimal(10,2) NOT NULL,
  `discount` decimal(10,2) DEFAULT NULL,
  `discount_perc` decimal(10,2) DEFAULT NULL,
  `taxable_value` decimal(10,2) DEFAULT NULL,
  `CGST` decimal(10,2) DEFAULT NULL,
  `cgstvalue` decimal(10,2) DEFAULT NULL,
  `SGST` decimal(10,2) DEFAULT NULL,
  `sgstvalue` decimal(10,2) DEFAULT NULL,
  `IGST` decimal(10,2) DEFAULT NULL,
  `igstvalue` decimal(10,2) DEFAULT NULL,
  `Total` decimal(10,2) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ss_deduction`
--

CREATE TABLE `ss_deduction` (
  `id` int(11) NOT NULL,
  `emp_id` text DEFAULT NULL,
  `parent_id` text DEFAULT NULL,
  `tmpl_id` text DEFAULT NULL,
  `deduction_name` text DEFAULT NULL,
  `deduction_rate` text DEFAULT NULL,
  `deduction_amount` text DEFAULT NULL,
  `calc_amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `ss_earning`
--

CREATE TABLE `ss_earning` (
  `id` int(11) NOT NULL,
  `emp_id` text DEFAULT NULL,
  `parent_id` text DEFAULT NULL,
  `tmpl_id` text DEFAULT NULL,
  `earning_name` text DEFAULT NULL,
  `earning_rate` text DEFAULT NULL,
  `earning_amount` text DEFAULT NULL,
  `calc_amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `temp_advance`
--

CREATE TABLE `temp_advance` (
  `id` int(11) NOT NULL,
  `client_code` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `client_name` text DEFAULT NULL,
  `site_name` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `bank_branch` text DEFAULT NULL,
  `ifsc_code` text DEFAULT NULL,
  `account_no` text DEFAULT NULL,
  `from_bank` text DEFAULT NULL,
  `to_bank` text DEFAULT NULL,
  `vendor_comm` text DEFAULT NULL,
  `pay_date` text DEFAULT NULL,
  `pay_detail` text DEFAULT NULL,
  `amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `branch_id` text DEFAULT NULL,
  `company_id` text DEFAULT NULL,
  `error` text DEFAULT NULL,
  `particular` text DEFAULT NULL,
  `from_bank_acno` text DEFAULT NULL,
  `from_bank_ifsc` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `temp_attendance`
--

CREATE TABLE `temp_attendance` (
  `id` int(11) NOT NULL,
  `client_code` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `site_name` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `at_1` text DEFAULT NULL,
  `at_2` text DEFAULT NULL,
  `at_3` text DEFAULT NULL,
  `at_4` text DEFAULT NULL,
  `at_5` text DEFAULT NULL,
  `at_6` text DEFAULT NULL,
  `at_7` text DEFAULT NULL,
  `at_8` text DEFAULT NULL,
  `at_9` text DEFAULT NULL,
  `at_10` text DEFAULT NULL,
  `at_11` text DEFAULT NULL,
  `at_12` text DEFAULT NULL,
  `at_13` text DEFAULT NULL,
  `at_14` text DEFAULT NULL,
  `at_15` text DEFAULT NULL,
  `at_16` text DEFAULT NULL,
  `at_17` text DEFAULT NULL,
  `at_18` text DEFAULT NULL,
  `at_19` text DEFAULT NULL,
  `at_20` text DEFAULT NULL,
  `at_21` text DEFAULT NULL,
  `at_22` text DEFAULT NULL,
  `at_23` text DEFAULT NULL,
  `at_24` text DEFAULT NULL,
  `at_25` text DEFAULT NULL,
  `at_26` text DEFAULT NULL,
  `at_27` text DEFAULT NULL,
  `at_28` text DEFAULT NULL,
  `at_29` text DEFAULT NULL,
  `at_30` text DEFAULT NULL,
  `at_31` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `branch_id` text DEFAULT NULL,
  `company_id` text DEFAULT NULL,
  `error` text DEFAULT NULL,
  `att_type` text DEFAULT NULL,
  `remark` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `temp_employee`
--

CREATE TABLE `temp_employee` (
  `id` int(11) NOT NULL,
  `employee_code` int(255) DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `initial` varchar(100) DEFAULT NULL,
  `first_name` text DEFAULT NULL,
  `middle_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `gender` varchar(100) DEFAULT NULL,
  `dob` text DEFAULT NULL,
  `date_of_joining` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `pincode` varchar(100) DEFAULT NULL,
  `country` text DEFAULT NULL,
  `phone1` text DEFAULT NULL,
  `phone2` text DEFAULT NULL,
  `email_id` text DEFAULT NULL,
  `contact_person` text DEFAULT NULL,
  `contact_mobile` text DEFAULT NULL,
  `p_address` text DEFAULT NULL,
  `p_city` text DEFAULT NULL,
  `p_state` text DEFAULT NULL,
  `p_pincode` text DEFAULT NULL,
  `p_country` text DEFAULT NULL,
  `p_phone1` text DEFAULT NULL,
  `p_phone2` text DEFAULT NULL,
  `contact_relation` text DEFAULT NULL,
  `contact_email` varchar(100) DEFAULT NULL,
  `marital_status` text DEFAULT NULL,
  `mrg_date` text DEFAULT NULL,
  `cast` text DEFAULT NULL,
  `category` text DEFAULT NULL,
  `native_place` text DEFAULT NULL,
  `blood_group` text DEFAULT NULL,
  `driving_license` text DEFAULT NULL,
  `pancard_no` text DEFAULT NULL,
  `aadhar_no` text DEFAULT NULL,
  `passport_no` text DEFAULT NULL,
  `uan_no` text DEFAULT NULL,
  `passport_valid_date` text DEFAULT NULL,
  `lang1` varchar(100) DEFAULT NULL,
  `lang2` varchar(100) DEFAULT NULL,
  `lang3` varchar(100) DEFAULT NULL,
  `lang4` varchar(100) DEFAULT NULL,
  `lang5` varchar(100) DEFAULT NULL,
  `hobby1` varchar(100) DEFAULT NULL,
  `hobby2` varchar(100) DEFAULT NULL,
  `hobby3` varchar(100) DEFAULT NULL,
  `hobby4` varchar(100) DEFAULT NULL,
  `bank_name` text DEFAULT NULL,
  `account_no` text DEFAULT NULL,
  `bank_address` text DEFAULT NULL,
  `bank_city` text DEFAULT NULL,
  `bank_state` text DEFAULT NULL,
  `bank_ifsc` text DEFAULT NULL,
  `bank_micr` text DEFAULT NULL,
  `cancelled_cheque_image` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `branch_id` varchar(10) DEFAULT NULL,
  `company_id` varchar(10) DEFAULT NULL,
  `created_by` varchar(10) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` varchar(10) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` varchar(10) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `department` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `reporting_manager` text DEFAULT NULL,
  `reporting_user` text DEFAULT NULL,
  `separation_type` text DEFAULT NULL,
  `separate_reason` text DEFAULT NULL,
  `date_of_separation` text DEFAULT NULL,
  `notice_period` text DEFAULT NULL,
  `last_working_date_sp` text DEFAULT NULL,
  `handover_given_to` text DEFAULT NULL,
  `salary_template` text DEFAULT NULL,
  `gross_salary` int(11) DEFAULT NULL,
  `gross_salary_type` text DEFAULT NULL,
  `basic_salary` int(11) DEFAULT NULL,
  `basic_salary_type` text DEFAULT NULL,
  `sal_from_date` text DEFAULT NULL,
  `sal_to_date` text DEFAULT NULL,
  `pf_no` text DEFAULT NULL,
  `esis_no` text DEFAULT NULL,
  `pf_applicable` text DEFAULT NULL,
  `ac_holder_name` text DEFAULT NULL,
  `card_no` text DEFAULT NULL,
  `da_term` text DEFAULT NULL,
  `basic_term` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `em_status` varchar(30) DEFAULT 'Working',
  `error` text DEFAULT NULL,
  `f_initial` text DEFAULT NULL,
  `f_relative_name` text DEFAULT NULL,
  `f_gender` text DEFAULT NULL,
  `f_relation` text DEFAULT NULL,
  `f_dob` text DEFAULT NULL,
  `f_age` text DEFAULT NULL,
  `f_minor` varchar(50) DEFAULT NULL,
  `f_guardian` text DEFAULT NULL,
  `f_address` text DEFAULT NULL,
  `f_contact` text DEFAULT NULL,
  `f_email` text DEFAULT NULL,
  `f_sharepf` text DEFAULT NULL,
  `f_shareesic` text DEFAULT NULL,
  `emp_company_name` text DEFAULT NULL,
  `emp_designation` text DEFAULT NULL,
  `emp_address` text DEFAULT NULL,
  `emp_city` text DEFAULT NULL,
  `emp_state` text DEFAULT NULL,
  `emp_country` text DEFAULT NULL,
  `emp_pincode` text DEFAULT NULL,
  `emp_joined_date` text DEFAULT NULL,
  `last_working_date` text DEFAULT NULL,
  `annual_ctc` text DEFAULT NULL,
  `monthly_ctc` text DEFAULT NULL,
  `reporting_to` text DEFAULT NULL,
  `reporting_to_designation` text DEFAULT NULL,
  `reporting_to_email` text DEFAULT NULL,
  `reporting_to_contact` text DEFAULT NULL,
  `gross_income` text DEFAULT NULL,
  `gross_tds` text DEFAULT NULL,
  `gross_pt` text DEFAULT NULL,
  `total_pt` text DEFAULT NULL,
  `doc_type` text DEFAULT NULL,
  `passing_year` text DEFAULT NULL,
  `doc_name` text DEFAULT NULL,
  `doc_status` text DEFAULT NULL,
  `doc_remark` text DEFAULT NULL,
  `doc_image` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `temp_manpower`
--

CREATE TABLE `temp_manpower` (
  `id` int(11) NOT NULL,
  `ma_date` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `client_code` text DEFAULT NULL,
  `at_day` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `final_amount` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `shift` text DEFAULT NULL,
  `in_time` text DEFAULT NULL,
  `out_time` text DEFAULT NULL,
  `total_hours` text DEFAULT NULL,
  `rate` text DEFAULT NULL,
  `count` text DEFAULT NULL,
  `supervisior` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `created_by` text DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `error` text DEFAULT NULL,
  `at_type` text DEFAULT NULL,
  `ot_hours` text DEFAULT NULL,
  `ot_rate` text DEFAULT NULL,
  `ot_final_amount` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `temp_work_order`
--

CREATE TABLE `temp_work_order` (
  `id` int(11) NOT NULL,
  `client_name` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `work_order_type` text DEFAULT NULL,
  `work_order_no` text DEFAULT NULL,
  `work_order_date` text DEFAULT NULL,
  `igm_no` text DEFAULT NULL,
  `importer_name` text DEFAULT NULL,
  `cha_name` text DEFAULT NULL,
  `vendor` text DEFAULT NULL,
  `item_no` text DEFAULT NULL,
  `container_no` text DEFAULT NULL,
  `size` text DEFAULT NULL,
  `vehicle_no` text DEFAULT NULL,
  `total_cargo_pkgs` text DEFAULT NULL,
  `total_cargo_weight` text DEFAULT NULL,
  `destuff_pkgs` text DEFAULT NULL,
  `destuff_weight` text DEFAULT NULL,
  `equipment_name` text DEFAULT NULL,
  `percentage_exam` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `remarks` text DEFAULT NULL,
  `hours` text DEFAULT NULL,
  `cbm` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `created_by` text DEFAULT NULL,
  `error` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `users`
--

CREATE TABLE `users` (
  `id` int(11) NOT NULL,
  `first_name` text DEFAULT NULL,
  `password` text DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `address` text DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `pincode` varchar(10) DEFAULT NULL,
  `role` text DEFAULT NULL,
  `profile_image` text DEFAULT NULL,
  `company_id` int(11) DEFAULT NULL,
  `branch` text DEFAULT NULL,
  `last_login_ip` text DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `last_logout` datetime DEFAULT NULL,
  `lock_screen` enum('N','Y') DEFAULT 'N',
  `lock_url` text DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `disabled_by` int(11) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `modules` text DEFAULT NULL,
  `company_name` text DEFAULT NULL,
  `branch_name` text DEFAULT NULL,
  `last_name` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `branch_id` varchar(20) DEFAULT NULL,
  `segments` text DEFAULT NULL,
  `main_module` text DEFAULT NULL,
  `site_id` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `onboarding` text DEFAULT '0',
  `client_code` text DEFAULT NULL,
  `fullname` text DEFAULT NULL,
  `gender` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_access`
--

CREATE TABLE `user_access` (
  `id` int(11) NOT NULL,
  `user_id` varchar(100) DEFAULT NULL,
  `segment` varchar(100) DEFAULT NULL,
  `main_module` varchar(100) DEFAULT NULL,
  `module_name` varchar(100) DEFAULT NULL,
  `module_id` varchar(100) DEFAULT NULL,
  `add_access` varchar(50) DEFAULT NULL,
  `edit_access` varchar(50) DEFAULT NULL,
  `delete_access` varchar(50) DEFAULT NULL,
  `export_access` varchar(50) DEFAULT NULL,
  `other_access` text DEFAULT NULL,
  `print_access` varchar(50) DEFAULT NULL,
  `active` enum('0','1') DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `user_search`
--

CREATE TABLE `user_search` (
  `id` int(11) NOT NULL,
  `search_fields` text DEFAULT NULL,
  `search_date_field` text DEFAULT NULL,
  `search_from_date` text DEFAULT NULL,
  `search_to_date` text DEFAULT NULL,
  `search_keyword` text DEFAULT NULL,
  `search_by` int(11) DEFAULT NULL,
  `search_on` datetime DEFAULT NULL,
  `ref_no` text DEFAULT NULL,
  `search_from_month` text DEFAULT NULL,
  `search_to_month` text DEFAULT NULL,
  `search_from_year` text DEFAULT NULL,
  `search_to_year` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `site_id` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `vendors`
--

CREATE TABLE `vendors` (
  `id` int(11) NOT NULL,
  `vendor_name` text NOT NULL,
  `email_id` text DEFAULT NULL,
  `address` text DEFAULT NULL,
  `phone` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `statecode` text DEFAULT NULL,
  `contactable_person` text DEFAULT NULL,
  `pancard_no` varchar(50) DEFAULT NULL,
  `registration_no` varchar(20) DEFAULT NULL,
  `gst_no` varchar(20) DEFAULT NULL,
  `ac_no` varchar(17) DEFAULT NULL,
  `bank_name` varchar(100) DEFAULT NULL,
  `ifsc_code` varchar(15) DEFAULT NULL,
  `micr_no` varchar(20) DEFAULT NULL,
  `branch_name` text DEFAULT NULL,
  `hide` enum('N','Y') DEFAULT 'N',
  `created_on` datetime DEFAULT NULL,
  `created_by` int(11) DEFAULT NULL,
  `modified_on` datetime DEFAULT NULL,
  `modified_by` int(11) DEFAULT NULL,
  `disabled_on` datetime DEFAULT NULL,
  `disabled_by` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_136_12_2024`
--

CREATE TABLE `wages_136_12_2024` (
  `id` int(9) UNSIGNED NOT NULL,
  `srno` text DEFAULT NULL,
  `employee_code` text DEFAULT NULL,
  `employee_name` text DEFAULT NULL,
  `rank` text DEFAULT NULL,
  `month_day` text DEFAULT NULL,
  `present_days` text DEFAULT NULL,
  `weekly_off` text DEFAULT NULL,
  `ot_dayshrs` text DEFAULT NULL,
  `absent` text DEFAULT NULL,
  `total_pay_days` text DEFAULT NULL,
  `basicf` text DEFAULT NULL,
  `daf` text DEFAULT NULL,
  `hra_f` text DEFAULT NULL,
  `specials_allowancef` text DEFAULT NULL,
  `other_allownce_f` text DEFAULT NULL,
  `bonusf0` text DEFAULT NULL,
  `ot_amount` text DEFAULT NULL,
  `grossf` text DEFAULT NULL,
  `basice` text DEFAULT NULL,
  `dae` text DEFAULT NULL,
  `hrae` text DEFAULT NULL,
  `special_allowancee` text DEFAULT NULL,
  `othre_allowancee` text DEFAULT NULL,
  `bonuse` text DEFAULT NULL,
  `total_eraned` text DEFAULT NULL,
  `pf` text DEFAULT NULL,
  `esic` text DEFAULT NULL,
  `pt` text DEFAULT NULL,
  `deduction` text DEFAULT NULL,
  `net_salary` text DEFAULT NULL,
  `pf_` text DEFAULT NULL,
  `tax_before_value_with_ot_amount` text DEFAULT NULL,
  `service_charges` text DEFAULT NULL,
  `after_tax_value` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_139_12_2024`
--

CREATE TABLE `wages_139_12_2024` (
  `id` int(9) UNSIGNED NOT NULL,
  `srno` text DEFAULT NULL,
  `emp_code` text DEFAULT NULL,
  `employee_name` text DEFAULT NULL,
  `agency` text DEFAULT NULL,
  `grade` text DEFAULT NULL,
  `gander` text DEFAULT NULL,
  `doj` text DEFAULT NULL,
  `dol` text DEFAULT NULL,
  `status` text DEFAULT NULL,
  `uan_no` text DEFAULT NULL,
  `esic_no` text DEFAULT NULL,
  `ot_rate` text DEFAULT NULL,
  `monthlydays` text DEFAULT NULL,
  `present_day` text DEFAULT NULL,
  `arrears` text DEFAULT NULL,
  `ot_hours` text DEFAULT NULL,
  `public_holiday_extra_paid_day` text DEFAULT NULL,
  `basic` text DEFAULT NULL,
  `da` text DEFAULT NULL,
  `sub_total_a` text DEFAULT NULL,
  `hra` text DEFAULT NULL,
  `other_allowance` text DEFAULT NULL,
  `washing_allowance` text DEFAULT NULL,
  `bonus` text DEFAULT NULL,
  `leave_encashment` text DEFAULT NULL,
  `gross` text DEFAULT NULL,
  `earned_basic` text DEFAULT NULL,
  `earned_da` text DEFAULT NULL,
  `sub_total__basic__da` text DEFAULT NULL,
  `earned_hra` text DEFAULT NULL,
  `earned_other_allowance` text DEFAULT NULL,
  `earned_washing_allowance` text DEFAULT NULL,
  `earned_bonus` text DEFAULT NULL,
  `earned_leave_encashment` text DEFAULT NULL,
  `earned_ot` text DEFAULT NULL,
  `earned_arrears` text DEFAULT NULL,
  `incentive_december2024` text DEFAULT NULL,
  `earned_gross` text DEFAULT NULL,
  `pf_12` text DEFAULT NULL,
  `esic_075` text DEFAULT NULL,
  `mlwf` text DEFAULT NULL,
  `pt` text DEFAULT NULL,
  `employee_total_deduction` text DEFAULT NULL,
  `net_take_home` text DEFAULT NULL,
  `pf_13` text DEFAULT NULL,
  `esic_325` text DEFAULT NULL,
  `employer_total_deduction` text DEFAULT NULL,
  `sub_total` text DEFAULT NULL,
  `mf_7` text DEFAULT NULL,
  `ctc` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_140_11_2024`
--

CREATE TABLE `wages_140_11_2024` (
  `id` int(9) UNSIGNED NOT NULL,
  `sr_no` text DEFAULT NULL,
  `emp_id` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `doj` text DEFAULT NULL,
  `designation` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `warehouse` text DEFAULT NULL,
  `fmcg` text DEFAULT NULL,
  `total_present` text DEFAULT NULL,
  `total_wo` text DEFAULT NULL,
  `total_leave` text DEFAULT NULL,
  `pl` text DEFAULT NULL,
  `payable_days` text DEFAULT NULL,
  `arrear_days` text DEFAULT NULL,
  `ot_hrs` text DEFAULT NULL,
  `extra_day_pay` text DEFAULT NULL,
  `night_shift_allowance_days` text DEFAULT NULL,
  `ee_pf` text DEFAULT NULL,
  `ee_esic` text DEFAULT NULL,
  `pt` text DEFAULT NULL,
  `actual_net` text DEFAULT NULL,
  `category` text DEFAULT NULL,
  `mw` text DEFAULT NULL,
  `basic` text DEFAULT NULL,
  `hra` text DEFAULT NULL,
  `bonus` text DEFAULT NULL,
  `spl` text DEFAULT NULL,
  `gross` text DEFAULT NULL,
  `er_pf` text DEFAULT NULL,
  `er_esic` text DEFAULT NULL,
  `insurance` text DEFAULT NULL,
  `total_ctc` text DEFAULT NULL,
  `basic_arrear` text DEFAULT NULL,
  `hra_arrear` text DEFAULT NULL,
  `bonus_arrear` text DEFAULT NULL,
  `spl_arrear` text DEFAULT NULL,
  `ot_amount` text DEFAULT NULL,
  `tc_amount` text DEFAULT NULL,
  `aa_amount` text DEFAULT NULL,
  `night_shift_amount` text DEFAULT NULL,
  `incentive` text DEFAULT NULL,
  `otherearnings` text DEFAULT NULL,
  `referal_bonus` text DEFAULT NULL,
  `travel_allowance` text DEFAULT NULL,
  `leave_encashment` text DEFAULT NULL,
  `lwf_ee` text DEFAULT NULL,
  `other_ded` text DEFAULT NULL,
  `net_pay` text DEFAULT NULL,
  `lwf_er` text DEFAULT NULL,
  `service_fees` text DEFAULT NULL,
  `total_billable_cost` text DEFAULT NULL,
  `gst` text DEFAULT NULL,
  `total_invoice_amt` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_142_1_2025`
--

CREATE TABLE `wages_142_1_2025` (
  `id` int(9) UNSIGNED NOT NULL,
  `srn` text DEFAULT NULL,
  `e_codesvendor` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `esic` text DEFAULT NULL,
  `pf` text DEFAULT NULL,
  `designation` text DEFAULT NULL,
  `location` text DEFAULT NULL,
  `doj_ddmmmyy` text DEFAULT NULL,
  `_basic_` text DEFAULT NULL,
  `_hra_` text DEFAULT NULL,
  `_spl_allo_` text DEFAULT NULL,
  `_stats_bonus_` text DEFAULT NULL,
  `_gross_` text DEFAULT NULL,
  `_pf_employee_` text DEFAULT NULL,
  `_esic_employee_` text DEFAULT NULL,
  `_pt_` text DEFAULT NULL,
  `_net_` text DEFAULT NULL,
  `_pf_employer_` text DEFAULT NULL,
  `_esic_employer_` text DEFAULT NULL,
  `_ctc_` text DEFAULT NULL,
  `_month_days_` text DEFAULT NULL,
  `_employee_worked_days_` text DEFAULT NULL,
  `_ot_hours_` text DEFAULT NULL,
  `_festival__national_holidays_pay_` text DEFAULT NULL,
  `_ot_amount_` text DEFAULT NULL,
  `_holiday_pay_` text DEFAULT NULL,
  `_attendance_bonus_` text DEFAULT NULL,
  `_incentive_amount_` text DEFAULT NULL,
  `_night_allowance_` text DEFAULT NULL,
  `_travel_allowance_` text DEFAULT NULL,
  `_total_gross_` text DEFAULT NULL,
  `_lwf_` text DEFAULT NULL,
  `_total_deductions_` text DEFAULT NULL,
  `_net_pay_` text DEFAULT NULL,
  `advance` text DEFAULT NULL,
  `transfer_amt` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_156_12_2024`
--

CREATE TABLE `wages_156_12_2024` (
  `id` int(9) UNSIGNED NOT NULL,
  `mobile` text DEFAULT NULL,
  `e_codesvendor` text DEFAULT NULL,
  `aadhaar_number` text DEFAULT NULL,
  `name` text DEFAULT NULL,
  `designation` text DEFAULT NULL,
  `newold` text DEFAULT NULL,
  `department` text DEFAULT NULL,
  `vendor` text DEFAULT NULL,
  `state` text DEFAULT NULL,
  `city` text DEFAULT NULL,
  `wh_name` text DEFAULT NULL,
  `doj_ddmmmyy` text DEFAULT NULL,
  `manager_name` text DEFAULT NULL,
  `lwd_ddmmmyy` text DEFAULT NULL,
  `activeinactive` text DEFAULT NULL,
  `notice_period_served_or_not` text DEFAULT NULL,
  `basic` text DEFAULT NULL,
  `hra` text DEFAULT NULL,
  `spl_allo` text DEFAULT NULL,
  `stats_bonus` text DEFAULT NULL,
  `gross` text DEFAULT NULL,
  `pf_employee` text DEFAULT NULL,
  `esic_employee` text DEFAULT NULL,
  `pt` text DEFAULT NULL,
  `lwf` text DEFAULT NULL,
  `net` text DEFAULT NULL,
  `pf_employer` text DEFAULT NULL,
  `esic_employer` text DEFAULT NULL,
  `lwf_employer` text DEFAULT NULL,
  `ctc` text DEFAULT NULL,
  `sf` text DEFAULT NULL,
  `total_billable_ctc` text DEFAULT NULL,
  `biz_working_days` text DEFAULT NULL,
  `biz_arrear_working_days` text DEFAULT NULL,
  `employee_worked_days` text DEFAULT NULL,
  `employee_worked_arrear_days` text DEFAULT NULL,
  `ot_hours` text DEFAULT NULL,
  `extra_days` text DEFAULT NULL,
  `notice_pay_recovery_days` text DEFAULT NULL,
  `basic_arrear` text DEFAULT NULL,
  `hra_arrear` text DEFAULT NULL,
  `stats_bonus_arrear` text DEFAULT NULL,
  `special_allow` text DEFAULT NULL,
  `special_allowance_arrear` text DEFAULT NULL,
  `ot_amount` text DEFAULT NULL,
  `extra_day_amount` text DEFAULT NULL,
  `attendance_bonus` text DEFAULT NULL,
  `fuel_reimbursement_amount` text DEFAULT NULL,
  `incentive_amount` text DEFAULT NULL,
  `night_allowance` text DEFAULT NULL,
  `travel_allowance` text DEFAULT NULL,
  `total_gross` text DEFAULT NULL,
  `pf_arrear` text DEFAULT NULL,
  `esic_arrear` text DEFAULT NULL,
  `lwf_employee` text DEFAULT NULL,
  `other_deductions` text DEFAULT NULL,
  `notice_payr_recovery` text DEFAULT NULL,
  `total_deductions` text DEFAULT NULL,
  `net_pay` text DEFAULT NULL,
  `total_bill_ctc` text DEFAULT NULL,
  `service_fees` text DEFAULT NULL,
  `gst` text DEFAULT NULL,
  `invoice_amount` text DEFAULT NULL,
  `utr_number` text DEFAULT NULL,
  `remarks` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wages_sheet`
--

CREATE TABLE `wages_sheet` (
  `id` int(11) NOT NULL,
  `client_code` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `client_name` text DEFAULT NULL,
  `at_month` text DEFAULT NULL,
  `at_year` text DEFAULT NULL,
  `emp_id` text DEFAULT NULL,
  `emp_name` text DEFAULT NULL,
  `designation` text DEFAULT NULL,
  `gender` text DEFAULT NULL,
  `basic` text DEFAULT NULL,
  `vda` text DEFAULT NULL,
  `total_basic_vda` text DEFAULT NULL,
  `special_allowance` text DEFAULT NULL,
  `hra` text DEFAULT NULL,
  `other_allowance` text DEFAULT NULL,
  `lww` text DEFAULT NULL,
  `bonus` text DEFAULT NULL,
  `gross` text DEFAULT NULL,
  `month_days` text DEFAULT NULL,
  `present_days` text DEFAULT NULL,
  `paid_holiday` text DEFAULT NULL,
  `actual_paid_days` text DEFAULT NULL,
  `ot_hours` decimal(10,2) DEFAULT NULL,
  `earn_basic` text NOT NULL,
  `earn_da` text NOT NULL,
  `earn_total_basic_da` text DEFAULT NULL,
  `earn_special_allowance_da` text DEFAULT NULL,
  `earn_hra_da` text DEFAULT NULL,
  `earn_other_allowance_da` text DEFAULT NULL,
  `earn_lww_da` text DEFAULT NULL,
  `earn_bonus_da` text DEFAULT NULL,
  `earn_ot_rate` text DEFAULT NULL,
  `earn_ot_amount` text DEFAULT NULL,
  `earn_total_gross_wages` text DEFAULT NULL,
  `pf_wages` text DEFAULT NULL,
  `esic_wages` text DEFAULT NULL,
  `deduct_pf` text DEFAULT NULL,
  `deduct_pt` text DEFAULT NULL,
  `deduct_esic` text DEFAULT NULL,
  `deduct_lwf` text DEFAULT NULL,
  `total_deduction` text DEFAULT NULL,
  `net_take_home` text DEFAULT NULL,
  `part_pf_percent` text DEFAULT NULL,
  `part_esic` text DEFAULT NULL,
  `part_t_shirt` text DEFAULT NULL,
  `part_ctc` text DEFAULT NULL,
  `part_service_charges` text DEFAULT NULL,
  `part_tctc` text DEFAULT NULL,
  `part_gst` text DEFAULT NULL,
  `part_invoice_amount` text DEFAULT NULL,
  `active` varchar(20) DEFAULT '0',
  `created_by` varchar(50) DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `advances` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Table structure for table `work_order`
--

CREATE TABLE `work_order` (
  `id` int(11) NOT NULL,
  `client_name` text DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `work_order_type` text DEFAULT NULL,
  `work_order_no` text DEFAULT NULL,
  `work_order_date` text DEFAULT NULL,
  `igm_no` text DEFAULT NULL,
  `importer_name` text DEFAULT NULL,
  `cha_name` text DEFAULT NULL,
  `vendor` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0',
  `created_on` text DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `modified_by` varchar(255) DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `disabled_by` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `work_order_detail`
--

CREATE TABLE `work_order_detail` (
  `id` int(11) NOT NULL,
  `parent_id` int(11) DEFAULT NULL,
  `item_no` text DEFAULT NULL,
  `container_no` text DEFAULT NULL,
  `size` text DEFAULT NULL,
  `seal_no` text DEFAULT NULL,
  `vehicle_no` text DEFAULT NULL,
  `arrival_date` text DEFAULT NULL,
  `total_cargo_pkgs` text DEFAULT NULL,
  `total_cargo_weight` text DEFAULT NULL,
  `destuff_pkgs` text DEFAULT NULL,
  `destuff_weight` text DEFAULT NULL,
  `percentage_exam` text DEFAULT NULL,
  `equipment_name` text DEFAULT NULL,
  `remarks` text DEFAULT NULL,
  `hours` text DEFAULT NULL,
  `cbm` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `active` text DEFAULT '0',
  `disabled_by` text DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `created_by` text DEFAULT NULL,
  `gang_name` text DEFAULT NULL,
  `cargo_name` text DEFAULT NULL,
  `invoice_number` text DEFAULT NULL,
  `allow_pkg` text DEFAULT NULL,
  `allow_weight` text DEFAULT NULL,
  `export_party` text DEFAULT NULL,
  `cal_rate` text DEFAULT NULL,
  `cal_amount` text DEFAULT NULL,
  `wo_cal_type` varchar(255) DEFAULT NULL,
  `invoice_generated` varchar(20) DEFAULT '0',
  `invoice_no` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `work_order_type`
--

CREATE TABLE `work_order_type` (
  `id` int(11) NOT NULL,
  `work_order_type` text DEFAULT NULL,
  `created_on` text DEFAULT NULL,
  `created_by` text DEFAULT NULL,
  `modified_by` text DEFAULT NULL,
  `modified_on` text DEFAULT NULL,
  `disabled_on` text DEFAULT NULL,
  `disabled_by` text DEFAULT NULL,
  `active` varchar(10) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

-- --------------------------------------------------------

--
-- Table structure for table `wo_wages`
--

CREATE TABLE `wo_wages` (
  `id` int(11) NOT NULL,
  `client_name` varchar(255) DEFAULT NULL,
  `client_id` text DEFAULT NULL,
  `work_order_type` varchar(150) DEFAULT NULL,
  `work_order_date` varchar(200) DEFAULT NULL,
  `item_no` text DEFAULT NULL,
  `container_no` text DEFAULT NULL,
  `size` text DEFAULT NULL,
  `total_cargo_pkgs` text DEFAULT NULL,
  `total_cargo_weight` text DEFAULT NULL,
  `percentage_exam` text DEFAULT NULL,
  `equipment_name` text DEFAULT NULL,
  `cal_rate` text DEFAULT NULL,
  `cal_amount` text DEFAULT NULL,
  `wo_cal_type` text DEFAULT NULL,
  `invoice_generated` varchar(20) DEFAULT '0',
  `invoice_no` text DEFAULT NULL,
  `at_month` varchar(100) DEFAULT NULL,
  `at_year` varchar(100) DEFAULT NULL,
  `woid` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `attendance`
--
ALTER TABLE `attendance`
  ADD PRIMARY KEY (`id`),
  ADD KEY `attendance_empcode` (`emp_code`(255)),
  ADD KEY `attendance_siteid` (`site_id`(255)),
  ADD KEY `attendance_client_code` (`client_code`(255)),
  ADD KEY `attendance_at_day` (`at_day`(255)),
  ADD KEY `attendance_at_month` (`at_month`(255)),
  ADD KEY `attendance_at_year` (`at_year`(255)),
  ADD KEY `attendance_att_type` (`att_type`(255)),
  ADD KEY `at_status` (`status`(3072)),
  ADD KEY `att_active` (`active`),
  ADD KEY `att_uniqueid` (`uniqueid`(3072)),
  ADD KEY `att_invoice_status` (`invoice_status`(3072)),
  ADD KEY `att_created_by` (`created_by`),
  ADD KEY `att_rank` (`rank`(3072));

--
-- Indexes for table `at_actual_charges`
--
ALTER TABLE `at_actual_charges`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `billing_header_setting`
--
ALTER TABLE `billing_header_setting`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `bill_details`
--
ALTER TABLE `bill_details`
  ADD PRIMARY KEY (`id`),
  ADD KEY `bill_details_auto_no` (`auto_no`(255)),
  ADD KEY `bill_details_rate_id` (`rate_id`(255)),
  ADD KEY `bill_details_client_id` (`client_id`(255)),
  ADD KEY `bill_details_invoice_month` (`invoice_month`(255)),
  ADD KEY `bill_details_invoice_year` (`invoice_year`(255));

--
-- Indexes for table `bill_supportings`
--
ALTER TABLE `bill_supportings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `branch`
--
ALTER TABLE `branch`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `charges_master`
--
ALTER TABLE `charges_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client_other_info`
--
ALTER TABLE `client_other_info`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `client_rates`
--
ALTER TABLE `client_rates`
  ADD PRIMARY KEY (`id`),
  ADD KEY `client_rates_clientid` (`client_id`),
  ADD KEY `client_rates_site_name` (`site_name`(255)),
  ADD KEY `client_rates_client_code` (`client_code`(255)),
  ADD KEY `client_rates_cc_contact_person` (`cc_contact_person`(255)),
  ADD KEY `client_rates_cc_address` (`cc_address`(255)),
  ADD KEY `client_rates_cc_contactno` (`cc_contactno`(255)),
  ADD KEY `client_rates_client_id` (`client_id`),
  ADD KEY `client_rates_cc_emailid` (`cc_emailid`(255));

--
-- Indexes for table `client_service_charges`
--
ALTER TABLE `client_service_charges`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `companies`
--
ALTER TABLE `companies`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `company_bank_detail`
--
ALTER TABLE `company_bank_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `debitnote_product_detail`
--
ALTER TABLE `debitnote_product_detail`
  ADD PRIMARY KEY (`payment_detail_id`);

--
-- Indexes for table `debit_note`
--
ALTER TABLE `debit_note`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `employee`
--
ALTER TABLE `employee`
  ADD PRIMARY KEY (`id`),
  ADD KEY `emp_active` (`active`),
  ADD KEY `emp_code_index` (`employee_code`),
  ADD KEY `em_first_name` (`first_name`(3072)),
  ADD KEY `em_last_name` (`last_name`(3072)),
  ADD KEY `em_rank` (`rank`(3072)),
  ADD KEY `em_clientid` (`client_id`(3072)),
  ADD KEY `em_siteid` (`site_id`(3072)),
  ADD KEY `em_status` (`em_status`(3072)),
  ADD KEY `emp_gender` (`gender`),
  ADD KEY `emp_phone1` (`phone1`(3072)),
  ADD KEY `emp_phone2` (`phone2`(3072)),
  ADD KEY `emp_pancard_no` (`pancard_no`(3072)),
  ADD KEY `emp_aadhar_no` (`aadhar_no`(3072)),
  ADD KEY `emp_passport_no` (`passport_no`(3072)),
  ADD KEY `emp_uan_no` (`uan_no`(3072)),
  ADD KEY `emp_bank_name` (`bank_name`(3072)),
  ADD KEY `emp_account_no` (`account_no`(3072)),
  ADD KEY `emp_bank_ifsc` (`bank_ifsc`(3072)),
  ADD KEY `emp_salary_template` (`salary_template`(3072)),
  ADD KEY `emp_gross_salary` (`gross_salary`),
  ADD KEY `emp_basic_salary` (`basic_salary`),
  ADD KEY `emp_pf_no` (`pf_no`(3072)),
  ADD KEY `emp_esis_no` (`esis_no`(3072)),
  ADD KEY `emp_perday_salary` (`perday_salary`(3072));

--
-- Indexes for table `employee_log`
--
ALTER TABLE `employee_log`
  ADD PRIMARY KEY (`id`),
  ADD KEY `emp_active` (`active`),
  ADD KEY `emp_code_index` (`employee_code`),
  ADD KEY `em_first_name` (`first_name`(3072)),
  ADD KEY `em_last_name` (`last_name`(3072)),
  ADD KEY `em_rank` (`rank`(3072)),
  ADD KEY `em_clientid` (`client_id`(3072)),
  ADD KEY `em_siteid` (`site_id`(3072)),
  ADD KEY `em_status` (`em_status`(3072)),
  ADD KEY `emp_gender` (`gender`),
  ADD KEY `emp_phone1` (`phone1`(3072)),
  ADD KEY `emp_phone2` (`phone2`(3072)),
  ADD KEY `emp_pancard_no` (`pancard_no`(3072)),
  ADD KEY `emp_aadhar_no` (`aadhar_no`(3072)),
  ADD KEY `emp_passport_no` (`passport_no`(3072)),
  ADD KEY `emp_uan_no` (`uan_no`(3072)),
  ADD KEY `emp_bank_name` (`bank_name`(3072)),
  ADD KEY `emp_account_no` (`account_no`(3072)),
  ADD KEY `emp_bank_ifsc` (`bank_ifsc`(3072)),
  ADD KEY `emp_salary_template` (`salary_template`(3072)),
  ADD KEY `emp_gross_salary` (`gross_salary`),
  ADD KEY `emp_basic_salary` (`basic_salary`),
  ADD KEY `emp_pf_no` (`pf_no`(3072)),
  ADD KEY `emp_esis_no` (`esis_no`(3072)),
  ADD KEY `emp_perday_salary` (`perday_salary`(3072));

--
-- Indexes for table `employee_test`
--
ALTER TABLE `employee_test`
  ADD PRIMARY KEY (`id`),
  ADD KEY `emp_active` (`active`),
  ADD KEY `emp_code_index` (`employee_code`),
  ADD KEY `em_first_name` (`first_name`(3072)),
  ADD KEY `em_last_name` (`last_name`(3072)),
  ADD KEY `em_rank` (`rank`(3072)),
  ADD KEY `em_clientid` (`client_id`(3072)),
  ADD KEY `em_siteid` (`site_id`(3072)),
  ADD KEY `em_status` (`em_status`(3072)),
  ADD KEY `emp_gender` (`gender`),
  ADD KEY `emp_phone1` (`phone1`(3072)),
  ADD KEY `emp_phone2` (`phone2`(3072)),
  ADD KEY `emp_pancard_no` (`pancard_no`(3072)),
  ADD KEY `emp_aadhar_no` (`aadhar_no`(3072)),
  ADD KEY `emp_passport_no` (`passport_no`(3072)),
  ADD KEY `emp_uan_no` (`uan_no`(3072)),
  ADD KEY `emp_bank_name` (`bank_name`(3072)),
  ADD KEY `emp_account_no` (`account_no`(3072)),
  ADD KEY `emp_bank_ifsc` (`bank_ifsc`(3072)),
  ADD KEY `emp_salary_template` (`salary_template`(3072)),
  ADD KEY `emp_gross_salary` (`gross_salary`),
  ADD KEY `emp_basic_salary` (`basic_salary`),
  ADD KEY `emp_pf_no` (`pf_no`(3072)),
  ADD KEY `emp_esis_no` (`esis_no`(3072)),
  ADD KEY `emp_perday_salary` (`perday_salary`(3072));

--
-- Indexes for table `emp_advance`
--
ALTER TABLE `emp_advance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_doc`
--
ALTER TABLE `emp_doc`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_docs`
--
ALTER TABLE `emp_docs`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_experience`
--
ALTER TABLE `emp_experience`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_nominee`
--
ALTER TABLE `emp_nominee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_salary_deduction`
--
ALTER TABLE `emp_salary_deduction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_salary_earning`
--
ALTER TABLE `emp_salary_earning`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_type`
--
ALTER TABLE `emp_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `emp_wages`
--
ALTER TABLE `emp_wages`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `equipment_master`
--
ALTER TABLE `equipment_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `excel_headers`
--
ALTER TABLE `excel_headers`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `gang_master`
--
ALTER TABLE `gang_master`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `holidays`
--
ALTER TABLE `holidays`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `holiday_detail`
--
ALTER TABLE `holiday_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `lables`
--
ALTER TABLE `lables`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `manpower_attendance`
--
ALTER TABLE `manpower_attendance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `modules`
--
ALTER TABLE `modules`
  ADD PRIMARY KEY (`module_id`);

--
-- Indexes for table `new_client`
--
ALTER TABLE `new_client`
  ADD PRIMARY KEY (`id`),
  ADD KEY `new_client_company_name` (`company_name`(255));

--
-- Indexes for table `orders`
--
ALTER TABLE `orders`
  ADD PRIMARY KEY (`id`),
  ADD KEY `orders_auto_no` (`auto_no`(255));

--
-- Indexes for table `payment_history`
--
ALTER TABLE `payment_history`
  ADD PRIMARY KEY (`payment_historyid`);

--
-- Indexes for table `payslip_settings`
--
ALTER TABLE `payslip_settings`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `product_gallery`
--
ALTER TABLE `product_gallery`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `purchase_bill`
--
ALTER TABLE `purchase_bill`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `purchase_bill_history`
--
ALTER TABLE `purchase_bill_history`
  ADD PRIMARY KEY (`payment_historyid`);

--
-- Indexes for table `purchase_bill_product_detail`
--
ALTER TABLE `purchase_bill_product_detail`
  ADD PRIMARY KEY (`payment_detail_id`);

--
-- Indexes for table `raise_ticket`
--
ALTER TABLE `raise_ticket`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `rate_charts`
--
ALTER TABLE `rate_charts`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salary_deduction`
--
ALTER TABLE `salary_deduction`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salary_earning`
--
ALTER TABLE `salary_earning`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `salary_slip`
--
ALTER TABLE `salary_slip`
  ADD PRIMARY KEY (`id`),
  ADD KEY `salary_slip_atmonth` (`at_month`(255)),
  ADD KEY `salary_slip_atyear` (`at_year`(255)),
  ADD KEY `salary_slip_at_month` (`at_month`(255)),
  ADD KEY `salary_slip_at_year` (`at_year`(255)),
  ADD KEY `salary_slip_site_id` (`site_id`(255));

--
-- Indexes for table `salary_template`
--
ALTER TABLE `salary_template`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `sale_bill`
--
ALTER TABLE `sale_bill`
  ADD PRIMARY KEY (`payment_id`);

--
-- Indexes for table `sale_bill_history`
--
ALTER TABLE `sale_bill_history`
  ADD PRIMARY KEY (`payment_historyid`);

--
-- Indexes for table `sale_bill_product_detail`
--
ALTER TABLE `sale_bill_product_detail`
  ADD PRIMARY KEY (`payment_detail_id`);

--
-- Indexes for table `ss_deduction`
--
ALTER TABLE `ss_deduction`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ss_deduction_parent_id` (`parent_id`(255)),
  ADD KEY `ss_deduction_deduction_name` (`deduction_name`(255));

--
-- Indexes for table `ss_earning`
--
ALTER TABLE `ss_earning`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ss_earning_parent_id` (`parent_id`(255));

--
-- Indexes for table `temp_advance`
--
ALTER TABLE `temp_advance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temp_attendance`
--
ALTER TABLE `temp_attendance`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temp_employee`
--
ALTER TABLE `temp_employee`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temp_manpower`
--
ALTER TABLE `temp_manpower`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `temp_work_order`
--
ALTER TABLE `temp_work_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `users`
--
ALTER TABLE `users`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_access`
--
ALTER TABLE `user_access`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `user_search`
--
ALTER TABLE `user_search`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `vendors`
--
ALTER TABLE `vendors`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_136_12_2024`
--
ALTER TABLE `wages_136_12_2024`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_139_12_2024`
--
ALTER TABLE `wages_139_12_2024`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_140_11_2024`
--
ALTER TABLE `wages_140_11_2024`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_142_1_2025`
--
ALTER TABLE `wages_142_1_2025`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_156_12_2024`
--
ALTER TABLE `wages_156_12_2024`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wages_sheet`
--
ALTER TABLE `wages_sheet`
  ADD PRIMARY KEY (`id`),
  ADD KEY `ws_client_code` (`client_code`(768)),
  ADD KEY `ws_client_id` (`client_id`(768)),
  ADD KEY `ws_client_name` (`client_name`(768)),
  ADD KEY `ws_atmonth` (`at_month`(768)),
  ADD KEY `ws_atyear` (`at_year`(768)),
  ADD KEY `ws_emp_name` (`emp_name`(768)),
  ADD KEY `ws_active` (`active`),
  ADD KEY `ws_invoice_amt` (`part_invoice_amount`(768)),
  ADD KEY `ws_present_days` (`present_days`(768)),
  ADD KEY `ws_othours` (`ot_hours`),
  ADD KEY `ws_gross` (`gross`(768));

--
-- Indexes for table `work_order`
--
ALTER TABLE `work_order`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `work_order_detail`
--
ALTER TABLE `work_order_detail`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `work_order_type`
--
ALTER TABLE `work_order_type`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `wo_wages`
--
ALTER TABLE `wo_wages`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `attendance`
--
ALTER TABLE `attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `at_actual_charges`
--
ALTER TABLE `at_actual_charges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `billing_header_setting`
--
ALTER TABLE `billing_header_setting`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bill_details`
--
ALTER TABLE `bill_details`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `bill_supportings`
--
ALTER TABLE `bill_supportings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `branch`
--
ALTER TABLE `branch`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `charges_master`
--
ALTER TABLE `charges_master`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `client_other_info`
--
ALTER TABLE `client_other_info`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `client_rates`
--
ALTER TABLE `client_rates`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `client_service_charges`
--
ALTER TABLE `client_service_charges`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `companies`
--
ALTER TABLE `companies`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `company_bank_detail`
--
ALTER TABLE `company_bank_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `debitnote_product_detail`
--
ALTER TABLE `debitnote_product_detail`
  MODIFY `payment_detail_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `debit_note`
--
ALTER TABLE `debit_note`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee`
--
ALTER TABLE `employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee_log`
--
ALTER TABLE `employee_log`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `employee_test`
--
ALTER TABLE `employee_test`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_advance`
--
ALTER TABLE `emp_advance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_doc`
--
ALTER TABLE `emp_doc`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_docs`
--
ALTER TABLE `emp_docs`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_experience`
--
ALTER TABLE `emp_experience`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_nominee`
--
ALTER TABLE `emp_nominee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_salary_deduction`
--
ALTER TABLE `emp_salary_deduction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_salary_earning`
--
ALTER TABLE `emp_salary_earning`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_type`
--
ALTER TABLE `emp_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `emp_wages`
--
ALTER TABLE `emp_wages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `equipment_master`
--
ALTER TABLE `equipment_master`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `excel_headers`
--
ALTER TABLE `excel_headers`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `gang_master`
--
ALTER TABLE `gang_master`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `holidays`
--
ALTER TABLE `holidays`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `holiday_detail`
--
ALTER TABLE `holiday_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `lables`
--
ALTER TABLE `lables`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `manpower_attendance`
--
ALTER TABLE `manpower_attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `modules`
--
ALTER TABLE `modules`
  MODIFY `module_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `new_client`
--
ALTER TABLE `new_client`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `orders`
--
ALTER TABLE `orders`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payment_history`
--
ALTER TABLE `payment_history`
  MODIFY `payment_historyid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `payslip_settings`
--
ALTER TABLE `payslip_settings`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `product_gallery`
--
ALTER TABLE `product_gallery`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchase_bill`
--
ALTER TABLE `purchase_bill`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchase_bill_history`
--
ALTER TABLE `purchase_bill_history`
  MODIFY `payment_historyid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `purchase_bill_product_detail`
--
ALTER TABLE `purchase_bill_product_detail`
  MODIFY `payment_detail_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `raise_ticket`
--
ALTER TABLE `raise_ticket`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `rate_charts`
--
ALTER TABLE `rate_charts`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salary_deduction`
--
ALTER TABLE `salary_deduction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salary_earning`
--
ALTER TABLE `salary_earning`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salary_slip`
--
ALTER TABLE `salary_slip`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `salary_template`
--
ALTER TABLE `salary_template`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sale_bill`
--
ALTER TABLE `sale_bill`
  MODIFY `payment_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sale_bill_history`
--
ALTER TABLE `sale_bill_history`
  MODIFY `payment_historyid` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `sale_bill_product_detail`
--
ALTER TABLE `sale_bill_product_detail`
  MODIFY `payment_detail_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ss_deduction`
--
ALTER TABLE `ss_deduction`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `ss_earning`
--
ALTER TABLE `ss_earning`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `temp_advance`
--
ALTER TABLE `temp_advance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `temp_attendance`
--
ALTER TABLE `temp_attendance`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `temp_employee`
--
ALTER TABLE `temp_employee`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `temp_manpower`
--
ALTER TABLE `temp_manpower`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `temp_work_order`
--
ALTER TABLE `temp_work_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `users`
--
ALTER TABLE `users`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_access`
--
ALTER TABLE `user_access`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `user_search`
--
ALTER TABLE `user_search`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `vendors`
--
ALTER TABLE `vendors`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_136_12_2024`
--
ALTER TABLE `wages_136_12_2024`
  MODIFY `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_139_12_2024`
--
ALTER TABLE `wages_139_12_2024`
  MODIFY `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_140_11_2024`
--
ALTER TABLE `wages_140_11_2024`
  MODIFY `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_142_1_2025`
--
ALTER TABLE `wages_142_1_2025`
  MODIFY `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_156_12_2024`
--
ALTER TABLE `wages_156_12_2024`
  MODIFY `id` int(9) UNSIGNED NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wages_sheet`
--
ALTER TABLE `wages_sheet`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `work_order`
--
ALTER TABLE `work_order`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `work_order_detail`
--
ALTER TABLE `work_order_detail`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `work_order_type`
--
ALTER TABLE `work_order_type`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT for table `wo_wages`
--
ALTER TABLE `wo_wages`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
