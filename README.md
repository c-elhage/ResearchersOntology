ResearchersOntology
===================
Atomium,
Ontology Search Engine,
Marc Nasrallah - Cyril El Hage -
École supérieure d'ingénieurs de Beyrouth - USJ - Lebanon
February 12, 2013
1-Requirements
In order to understand this project, you will need knowledge in the following:
• Ontologies (Very Good)
• Manchester OWL Syntax and DLQuery(Very Good)
• Java language(Very Good)
• HTML, Javascript and jQuery(Moderate)

2-Introduction
This project is an Ontology search engine. The engine allows to query individuals
from their object and data properties that are implemented in the Ontology.

3-Client Side
Since ontologies are still a new concept, developing a user interface is quite a challenge.
We decided that the client will not use the Manchester Syntax, and that the Query can be built dynamically. 
The Query’s complexity is therefore unknown. Here’s our idea for a user-friendly interface:
The user selects the property from a drop down list, then writes the query in a field. 
The user can then click on an ”and” or ”or” button, and will then have another drop down list-textfield-buttons 
family to continue their Query and build it. Meanwhile, clicking the ”and/or” buttons will update a string in javascript
that shows the format of the query. This is essential so that the server learns the hierarchy of the request and builds
a Query in Manchester Syntax.
Please note that the jQuery and javascript were tested on just a few browsers and on one computer, you Will have
to modify the param- eters in order for the animations and placement to work properly.
Any suggestions or fixes are welcome!

4-Server Side
The server gets the string containing the request’s format, and dynamically
communicates with the ontology to fill in the substring matches and build a
complete Query in Manchester Syntax. It will then send the final Query to the engine.


5 Adapting it to your ontology
The java code will work whatever the ontology. In case you would like to add your own ontology, 
you will have to modify the HTML and javascript (Drop down list items and their values). 
The printer also trims and modifies the strings representing the properties we created. 
All our properties have 2 capital letters at the end of their name representing their Domain.
The printer removes those 2 letters and thus modifies property names in the results. 
Finally you will have to modify the ontology file name when loading it in the ontology manager.
You should try running the project at first then fix the javascript and jQuery
For any bug report or questions/suggestions, please contact us at: cyrilhage@gmail.com or marc.nasrallah@gmail.com
You may redistribute the code in the hope that it will be useful. Thank you for mentioning us while doing so.
