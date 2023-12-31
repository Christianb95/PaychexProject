Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000003, 'California Income Tax', 'CA IT', TO_DATE('7/1/2004', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000000);
Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000004, 'California Unemployment Insurance', 'CA UI', TO_DATE('7/1/2004', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000000);
Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000005, 'New York Income Tax', 'NY IT', TO_DATE('10/1/2003', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000001);
Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000006, 'New York Unemployment Insurance', 'NY UI', TO_DATE('10/1/2003', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000001);
Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000007, 'Federal Income Tax', 'FED IT', TO_DATE('10/1/2003', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000002);
Insert into TAX
   (TAX_ID, TAX_NAME, TAX_SHORT_NAME, TAX_ACTIVE_DATE, 
    TAX_INACTIVE_DATE, TAX_LOCATION_ID)
 Values
   (2000008, 'Federal Unemployment Insurance', 'FED UI', TO_DATE('10/1/2003', 'MM/DD/YYYY'), 
    TO_DATE('12/31/4712', 'MM/DD/YYYY'), 1000002);
COMMIT;
