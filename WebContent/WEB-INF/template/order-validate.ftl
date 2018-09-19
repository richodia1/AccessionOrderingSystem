
Dear ${order.requestor.firstName!''} ${order.requestor.lastName},

IITA Genebank has received a request for germplasm using 
your email address "${order.requestor.email}". If you have not 
filed a request with IITA Genebank, please ignore this message.

Order ID: ${order.id}
Validation key: ${order.validationKey}

To confirm your germplasm request, click the following link:
${config['application.url']}/validate.jspx?id=${order.id}&key=${order.validationKey} 

Once your email address is validated, your order will be processed. 


<#include "genebank-footer.ftl">
 