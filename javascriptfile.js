
var query="n_dd1,n_tx2";
//query should give the hierarchy.
//server will get the values.

var i=1;
var lvl=0;
var ids = 6;
var divcounter =0;

//updates the query to give the request hierarchy
//the link being "and" or "or"
function updateQuery(clicked_name,newnamedd,newnametxt,link)
{
   var ind =  query.indexOf(clicked_name)+clicked_name.length;
    query = query.substr(0, ind)+"-"+link+"-"+newnamedd+","+
        newnametxt+"-"+"end "+link+"-"+query.substr(ind+1,query.length);

    var hiddentxt=document.getElementById("hiddentxt");
    hiddentxt.value = query;
}
/**
 * fixes heights with animation after adding elements
 */
function fixallheights(clicked_id)
{
    var obje = document.getElementById(clicked_id);
    var jeeh= $(obje).position().top;
    for(var g=0;g<document.all.length;g++)
        {
           
         if(parseInt(document.all[g].id.substr(1, document.all[g].id.length-1))>=0)
             {
            var to = $(document.all[g]).position().top;
            if(to>=jeeh+11)
             {
                 var lo = $(document.all[g]).position().left;
    
   $(document.all[g]).css({
    position: 'absolute',
    left: lo,
    top:to
}).animate({top: to + 20,left:lo});
             }
             }
        }
}


/**
 * creates new elements and calls updateQuery with and as parameter
 */
function andClicked(clicked_class,clicked_id)
{
    var obj = document.getElementById(clicked_id);
    var div1 = document.getElementById('secondarydiv'); 
    var clicked_c = parseInt(clicked_class);
    var label = document.createElement("label");
    
    label.innerHTML="and";
    label.className=clicked_c+1;
    label.id="a"+ids;
    ids++;
    label.style.color = "#FFFFFF";
    label.style.fontWeight="bold";
    var a = document.createElement("select");
    a.name="n_dd"+ids;
    a.innerHTML=  "<option value=\"First_NamePE\">First Name</option><option value=\"Last_NamePE\">Last Name</option><option value=\"PhonePE\">Phone Number</option><option value=\"EmailPE\">Email</option><option value=\"Current_LocationPE-LocationCO-LocationTR-LocationRC\">Location</option><option value=\"Date_of_BirthPE\">Date of Birth</option><option value=\"DateCO-DateTR-Date_of_PublicationPU\">Date of Publication, Conference...</option><option value=\"NameCO-NamePB-NamePR-NamePU-NameRC-NameTE-NameTH\">Name of Publisher, Team...</option><option value=\"BudgetPR-BudgetRC\">Budget</option><option value=\"DurationTR\">Duration of Trip</option><option value=\"ReasonTR\">Reason of Trip</option>  <option value=\"GenderPE\">Gender</option><option value=\"ProjectRE-ProjectRC-ProjectTH\">Project</option><option value=\"ResearcherCO-ResearcherPR-ResearcherPU-ResearcherRC-ResearcherTE-ResearcherTR\">Researcher</option><option value=\"ConferenceRE\">Conference</option><option value=\"CoworkerRE\">Coworker</option><option value=\"PublicationPB-PublicationRE-PublicationRC\">Publication</option><option value=\"PublisherPU\">Publisher</option><option value=\"Research_CenterPR-Research_CenterPU-Research_CenterRE\">Research Center</option><option value=\"TeamRE\">Team</option><option value=\"ThemePR\">Theme</option><option value=\"TripRE\">Trip</option><option value=\"Project_ManagerRE\">Is Project Manager</option><option value=\"has_Project_ManagerPR\">Has Project Manager</option><option value=\"Pilot_StudyPR\">Is Pilot Study</option><option value=\"has_Pilot_StudyPR\">Has Pilot Study</option>";
    a.left='10px';
    a.position="absolute";
    a.top='50px';
    a.className=clicked_c+1;
    a.id = "a"+ids;ids++;
    var b = document.createElement("input");
    b.name="n_tx"+ids;
    b.innerHTML=("<input type ='text'>");
    b.value="or/and";
    b.className=clicked_c+1;
    b.id = "a"+ids;ids++;
    var c = document.createElement("input");
    c.setAttribute("type", "button");
    c.setAttribute("value", "and");
    c.setAttribute("onclick", "andClicked(this.className,this.id)");
    c.setAttribute('style','-webkit-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; -moz-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; background-color:#5B74A8; border:1px solid #29447E;; padding:2px 6px;height:20px;color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px');
    c.className=clicked_c+1;
    
    c.id="a"+ids;ids++;
    var d = document.createElement("input");
    d.setAttribute("type", "button");
    d.setAttribute("value", "or");
    d.setAttribute("onclick", "orClicked(this.className,this.id)");
    d.setAttribute('style','-webkit-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; -moz-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; background-color:#5B74A8; border:1px solid #29447E;; padding:2px 6px;height:20px;color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px');
    
    d.className=clicked_c+1;
    d.id="a"+ids;ids++;
    
 var newdiv = document.createElement("div");
 newdiv.className=clicked_c+1;
 newdiv.id = "a"+ids;ids++;
 newdiv.style.position="absolute";
 newdiv.style.height ="12px";
 newdiv.style.borderBottom="2px solid #000000";
 newdiv.style.borderLeft="2px solid #000000";
 newdiv.style.width="100px";
    
    if (divcounter<=25 && document.getElementsByClassName(clicked_c+1).length==0)
    {
    $(div1).animate({
    marginLeft: '-=' +94
}, 600, function(){

});
    }
    var h,w;
    fixallheights(clicked_id);
    var addheight;
   if(clicked_c>lvl)
     {
        lvl = clicked_c;
     }
     var hh=$(document.getElementById(clicked_id)).position().top;
        
        addheight=hh+20;
     
    w=clicked_c+1;
    $(newdiv).hide().appendTo(div1).show('normal');
    $(label).hide().appendTo(div1).show('normal');        
    $(a).hide().appendTo(div1).show('normal');
    $(b).hide().appendTo(div1).show('normal');
    $(c).hide().appendTo(div1).show('normal');
    $(d).hide().appendTo(div1).show('normal');
    
    
    var l  = $(a).position().left;
    var t  = $(a).position().top;
    var lb = $(b).position().left;
    var tb = $(b).position().top;
    var lc = $(c).position().left;
    var tc = $(c).position().top;
    var ld = $(d).position().left;
    var td = $(d).position().top;
var decl = l*w*0.5-70;
var declbl = -22;
var decdd = 0;
var dectxt = 214;
var decand=dectxt+122;
var decor=decand+34;
$(newdiv).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight,left:decl-120-3});

$(label).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight+3,left:decl+declbl});
   

   $(a).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight,left:decl+decdd});
   
   $(b).css({
    position: 'absolute',
    left: l,
    top:tb
}).animate({top: tb + addheight - 7,left:decl+decdd+dectxt});
   $(c).css({
    position: 'absolute',
    left: l,
    top:tc
}).animate({top: tc + addheight-12,left:decl+decdd+decand});
   
   $(d).css({
    position: 'absolute',
    left: l,
    top:td
}).animate({top: td + addheight-12,left:decl+decdd+decor});
    i++;

updateQuery(document.getElementById("a"+
(parseInt(clicked_id.substr(1,clicked_id.length-1))-1)).name,
a.name,b.name,"and");

}
/**
 * creates new elements and calls updateQuery with or as parameter
 */
function orClicked(clicked_class,clicked_id)
{
    var div1 = document.getElementById('secondarydiv'); 
    var clicked_c = parseInt(clicked_class);
    var label = document.createElement("label");
    label.innerHTML="or ";
    label.className=clicked_c+1;
    label.id="a"+ids++;
    label.style.color = "#FFFFFF";
    label.style.fontWeight="bold";
    var a = document.createElement("select");
    a.innerHTML=  "<option value=\"First_NamePE\">First Name</option><option value=\"Last_NamePE\">Last Name</option><option value=\"PhonePE\">Phone Number</option><option value=\"EmailPE\">Email</option><option value=\"Current_LocationPE-LocationCO-LocationTR-LocationRC\">Location</option><option value=\"Date_of_BirthPE\">Date of Birth</option><option value=\"DateCO-DateTR-Date_of_PublicationPU\">Date of Publication, Conference...</option><option value=\"NameCO-NamePB-NamePR-NamePU-NameRC-NameTE-NameTH\">Name of Publisher, Team...</option><option value=\"BudgetPR-BudgetRC\">Budget</option><option value=\"DurationTR\">Duration of Trip</option><option value=\"ReasonTR\">Reason of Trip</option>  <option value=\"GenderPE\">Gender</option><option value=\"ProjectRE-ProjectRC-ProjectTH\">Project</option><option value=\"ResearcherCO-ResearcherPR-ResearcherPU-ResearcherRC-ResearcherTE-ResearcherTR\">Researcher</option><option value=\"ConferenceRE\">Conference</option><option value=\"CoworkerRE\">Coworker</option><option value=\"PublicationPB-PublicationRE-PublicationRC\">Publication</option><option value=\"PublisherPU\">Publisher</option><option value=\"Research_CenterPR-Research_CenterPU-Research_CenterRE\">Research Center</option><option value=\"TeamRE\">Team</option><option value=\"ThemePR\">Theme</option><option value=\"TripRE\">Trip</option><option value=\"Project_ManagerRE\">Is Project Manager</option><option value=\"has_Project_ManagerPR\">Has Project Manager</option><option value=\"Pilot_StudyPR\">Is Pilot Study</option><option value=\"has_Pilot_StudyPR\">Has Pilot Study</option>";
    a.left='10px';
    a.position="absolute";
    a.top='50px';
    a.className=clicked_c+1;
    a.id = "a"+ids++;
    a.name="n_dd"+ids;
    var b = document.createElement("input");
    b.name="n_tx"+ids;
    b.innerHTML=("<input type ='text'>");
    b.value="or/and";
    b.className=clicked_c+1;
    b.id = "a"+ids++;
    var c = document.createElement("input");
     c.setAttribute("type", "button");
    c.setAttribute("value", "and");
    c.setAttribute("onclick", "andClicked(this.className,this.id)");
     c.setAttribute('style','-webkit-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; -moz-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; background-color:#5B74A8; border:1px solid #29447E;; padding:2px 6px;height:20px;color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px');
   
    c.className=clicked_c+1;
    c.id="a"+ids++;
    var d = document.createElement("input");
   d.setAttribute("type", "button");
    d.setAttribute("value", "or");
    d.setAttribute("onclick", "orClicked(this.className,this.id)");
    d.setAttribute('style','-webkit-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; -moz-box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; box-shadow:rgba(0,0,0,0.0.1) 0 1px 0 0; background-color:#5B74A8; border:1px solid #29447E;; padding:2px 6px;height:20px;color:#fff;border-radius:5px;-moz-border-radius:5px;-webkit-border-radius:5px');
   
    d.className=clicked_c+1;
    d.id="a"+ids++;
    
 var newdiv = document.createElement("div");
 newdiv.className=clicked_c+1;
 newdiv.id = "a"+ids++;
 newdiv.style.position="absolute";
 newdiv.style.height ="12px";
 newdiv.style.borderBottom="2px solid #000000";
 newdiv.style.borderLeft="2px solid #000000";
 newdiv.style.width="100px";
    
    if (divcounter<=25 && document.getElementsByClassName(clicked_c+1).length==0)
    {
    $(div1).animate({
    marginLeft: '-=' +94
}, 600, function(){

});
    }

    var h,w;
    fixallheights(clicked_id);
    var addheight;
   if(clicked_c>lvl)
     {
        lvl = clicked_c;
     }
     var hh=$(document.getElementById(clicked_id)).position().top;
        
        addheight=hh+20;
     
    w=clicked_c+1;
    $(newdiv).hide().appendTo(div1).show('normal');
    $(label).hide().appendTo(div1).show('normal');        
    $(a).hide().appendTo(div1).show('normal');
    $(b).hide().appendTo(div1).show('normal');
    $(c).hide().appendTo(div1).show('normal');
    $(d).hide().appendTo(div1).show('normal');

    var l  = $(a).position().left;
    var t  = $(a).position().top;
    var lb = $(b).position().left;
    var tb = $(b).position().top;
    var lc = $(c).position().left;
    var tc = $(c).position().top;
    var ld = $(d).position().left;
    var td = $(d).position().top;
    var decl = l*w*0.5-67;
var declbl = -15;
var decdd = 0;
var dectxt = 214;
var decand=dectxt+122;
var decor=decand+34;
    

$(newdiv).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight,left:decl-120-3});

$(label).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight+3,left:decl+declbl});
   

   $(a).css({
    position: 'absolute',
    left: l,
    top:t
}).animate({top: t + addheight,left:decl+decdd});
   
   $(b).css({
    position: 'absolute',
    left: lb,
    top:tb
}).animate({top: tb + addheight - 7,left:decl+decdd+dectxt});
   $(c).css({
    position: 'absolute',
    left: lc,
    top:tc
}).animate({top: tc + addheight-12,left:decl+decdd+decand});
   
   $(d).css({
    position: 'absolute',
    left: ld,
    top:td
}).animate({top: td + addheight-12,left:decl+decdd+decor});
 
  i++;
  
updateQuery(document.getElementById("a"+
(parseInt(clicked_id.substr(1,clicked_id.length-1))-2)).name,
a.name,b.name,"or");        
    
}