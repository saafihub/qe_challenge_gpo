Feature: Generate Tax Relief Egress File

Background:
	Given Logged in to application as bookkeeper
	|username|password|
	|bk|bk|

@UI
Scenario: Validate BookKeeper Dashboard page
	Given user is on bookkeeper dashboard page
	Then user lands on the bookkeeper dashboard page
	And bookkeeper dashboard page header should be "Book Keeper Dashboard"

@UI
Scenario: Generate Tax Relief Egress File Button is visible
	Given user is on bookkeeper dashboard page
	Then verify Generate Tax Relief Egress File button should be displayed

@UI
Scenario: Generate Tax Relief Egress File Successfully
	Given user is on bookkeeper dashboard page
	When user click on the Generate Tax Relief Egress File button
	Then verify the "taxrelief.txt" file is generated

@UI
Scenario: Check file status in db once file process is triggered
	Given user establish a database connection
	When user execute query "SELECT natid FROM working_class_heroes"
	Then user should receive a non empty resultset

@UI
Scenario: Check the total count of records, if file has contents
	Given user is on bookkeeper dashboard page
	When user generated the file "taxrelief.txt"
	Then verify the total count of records written in "taxrelief.txt" file

@UI
Scenario: Check the total count, if file has empty records
	Given user is on bookkeeper dashboard page
	When user generated the file "taxreliefEmpty.txt"
	Then verify the total count of records written as 0 in "taxreliefEmpty.txt" file


