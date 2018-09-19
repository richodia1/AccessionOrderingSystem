
Dear ${order.requestor.firstName!''} ${order.requestor.lastName},

Your request has been validated. During the checkout process you have agreed
to terms of Standard Material Transfer Agreement (SMTA).

Contact the Plant Quarantine and Protection Services (PQPS)<#if order.requestor.country??>
of ${order.requestor.country} </#if>:

 1. Whether or not you need an import permit for the germplasm
 2. Phytosanitary certificate requirement

For PQPS contact information use https://www.ippc.int/ website.

Please note that in case we do not have a valid Phytosanitary certificate 
for selected germplasm, we will only consider sending it to you upon receiving
an official document endorsed by the Plant Quarantine and Protection Service
(PQPS) <#if order.requestor.country??>of ${order.requestor.country} </#if>stipulating that: 

 1. PQPS <#if order.requestor.country??>of ${order.requestor.country} </#if>allows you to import this germplasm. 
 2. You are aware this germplasm is not certified free of disease.
 3. You take responsibility for introducing germplasm-associated pathogens to the country.

Upon receiving these documents, your request will be processed providing our
stocks are compatible your request.

REQUEST #${order.id} DETAILS
============================

Shipping address
----------------
<#if order.requestor.firstName??>
${order.requestor.firstName!''} ${order.requestor.otherNames!''} ${order.requestor.lastName!''} - ${order.requestor.email}
${order.requestor.organization!''}
${order.requestor.address!''}
${order.requestor.email!''}
${order.requestor.postalCode!''} ${order.shipTo.city!''}
${order.requestor.country!''}
</#if>
<#if order.items??>Requested accessions
--------------------
<#list order.items as item>${item.item.name}
</#list></#if>


<#include "genebank-footer.ftl">