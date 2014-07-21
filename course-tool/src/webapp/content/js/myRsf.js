// RSF.js - primitive definitions for parsing RSF-rendered forms and bindings
// definitions placed in RSF namespace, following approach recommended in http://www.dustindiaz.com/namespace-your-javascript/

var myRSF = function() {

  /** control logging, set this to true to turn on logging or use the setLogging method */ 
  var logging = false;

  function $it(elementID) {
    return document.getElementById(elementID);
  }

  function invalidate(invalidated, EL, entry) {
    if (!EL) {
      RSF.log("invalidate null EL: " + invalidated + " " + entry);
      }
    var stack = RSF.parseEL(EL);
    invalidated[stack[0]] = entry;
    invalidated[stack[1]] = entry;
    invalidated.list.push(entry);
    RSF.log("invalidate " + EL);
    };

  function isInvalidated(invalidated, EL) {
    if (!EL) {
      RSF.log("isInvalidated null EL: " + invalidated);
      }
    var stack = RSF.parseEL(EL);
    var togo = invalidated[stack[0]] || invalidated[stack[1]];
    RSF.log("isInvalidated "+EL+" " + togo); 
    return togo;
    }

  function isFossil(element, input) {
    if (element.id && input.id == element.id + "-fossil") return true;
    return (input.name == element.name + "-fossil");
    }
    
  function normaliseBinding(element) {
    RSF.log("normaliseBinding name " + element.name + " id " + element.id);
    if (!element.name) return element.id;
    else return element.name == "virtual-el-binding"? "el-binding" : element.name;
    }

  var requestactive = false;
  var queuemap = new Object();
  
  function packAJAXRequest(method, url, parameters, callback) {
    return {method: method, url: url, parameters: parameters, callback: callback};
    }
    
  function wrapCallbacks(callbacks, wrapper) {
    var togo = new Object();
    for (var i in callbacks) {
      togo[i] = wrapper(callbacks[i]);
      }
    return togo;
    }

  // private defs for addEvent - see attribution comments below
  var addEvent_guid = 1;
  var addEvent_handlers = {};
  
  function handleEvent(event) {
    event = event || fixEvent(window.event);
    var returnValue = true;
    var handlers = addEvent_handlers[this.$$guid][event.type];
    
    for (var i in handlers) {
      if (!Object.prototype[i]) {
        this.$$handler = handlers[i];
        if (this.$$handler(event) === false) returnValue = false;
        }
      }

    if (this.$$handler) this.$$handler = null;
    return returnValue;
    }

  function fixEvent(event) {
    event.preventDefault = fixEvent.preventDefault;
    event.stopPropagation = fixEvent.stopPropagation;
    return event;
    }
    
  fixEvent.preventDefault = function() {
    this.returnValue = false;
    }
    
  fixEvent.stopPropagation = function() {
    this.cancelBubble = true;
    }
  
  function getEventFirer() {
    var listeners = {};
    return {
      addListener: function (listener, exclusions) {
        if (!listener.$$guid) listener.$$guid = addEvent_guid++;
        excludeids = [];
        if (exclusions) {
          for (var i in exclusions) {
            excludeids.push(exclusions[i].id);
            }
          }
        listeners[listener.$$guid] = {listener: listener, exclusions: excludeids};
        },
      fireEvent: function() {
        for (var i in listeners) {
          var lisrec = listeners[i];
          var excluded = false;
          for (var j in lisrec.exclusions) {
            var exclusion = lisrec.exclusions[j];
            RSF.log("Checking exclusion for " + exclusion);
            if (primaryElements[exclusion]) {
              RSF.log("Excluded");
              excluded = true; break;
              }
            }
          if (!excluded) {
            try {
              RSF.log("Firing to listener " + i + " with arguments " + arguments);
              lisrec.listener.apply(null, arguments);
              }
            catch (e) {
              RSF.log("Received exception " + e.message + " e " +e);
               throw (e);       
              }
            }
          }
        }
      };
    }
    
  /** Returns the standard registered firer for this field, creating if
    necessary. This will have method "addListener" and "fireEvent" **/
    function getElementFirer (element) {
      if (!element.$$RSF_firer) {
        element.$$RSF_firer = getEventFirer();
        }
      return element.$$RSF_firer;
      }
    // This is set in getModelFirer, and checked in fireEvent
    var primaryElements = {};
      
    function copyObject(target, newel) {
      for (var i in newel) {
        RSF.log("Copied value " + newel[i] + " for key " + i);
        target[i] = newel[i];
        }
      }
    function clearObject(target, newel) {
      for (var i in newel) {
        delete target[i];
        }
      }
    // a THING, that when given "elements", returns a THING, that when it is
    // given a CALLBACK, returns a THING, that does the SAME as the CALLBACK,
    // only with wrappers which are bound to the value that ELEMENTS had at
    // the function start
    function primaryRestorationWrapper() {
      var elementscopy = {};
      copyObject(elementscopy, primaryElements);
      RSF.log("Primary elements storing in wrapper");
      
      return function(callback) {
        return function () {
          copyObject(primaryElements, elementscopy);
          try {
            callback.apply(null, arguments);
            }
          finally {
            RSF.log("Restoration clearing");
            clearObject(primaryElements, elementscopy);
            RSF.log("Restoration cleared");
            }
          }
        }
      }

   /* parseUri 1.2; MIT License
   By Steven Levithan <http://stevenlevithan.com> 
   http://blog.stevenlevithan.com/archives/parseuri
   */
   var parseUri = function (source) {
      var o = parseUri.options,
         value = o.parser[o.strictMode ? "strict" : "loose"].exec(source);
      
      for (var i = 0, uri = {}; i < 14; i++) {
         uri[o.key[i]] = value[i] || "";
      }
      
      uri[o.q.name] = {};
      uri[o.key[12]].replace(o.q.parser, function ($0, $1, $2) {
         if ($1) uri[o.q.name][$1] = $2;
      });
      
      return uri;
   }
   parseUri.options = {
      strictMode: false,
      key: ["source","protocol","authority","userInfo","user","password","host","port","relative","path","directory","file","query","anchor"],
      q: {
         name: "queryKey",
         parser: /(?:^|&)([^&=]*)=?([^&]*)/g
      },
      parser: {
         strict: /^(?:([^:\/?#]+):)?(?:\/\/((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?))?((((?:[^?#\/]*\/)*)([^?#]*))(?:\?([^#]*))?(?:#(.*))?)/,
         loose: /^(?:(?![^:@]+:[^:@\/]*@)([^:\/?#.]+):)?(?:\/\/)?((?:(([^:@]*):?([^:@]*))?@)?([^:\/?#]*)(?::(\d*))?)(((\/(?:[^?#](?![^?#\/]*\.[^?#\/.]+(?:[?#]|$)))*\/?)?([^?#\/]*))(?:\?([^#]*))?(?:#(.*))?)/
      }
   }

    function ignorableNode(node) {
      return node.tagName.toLowerCase() == 'select';
      }

    function getNextNode(iterator) {
      if (iterator.node.firstChild && !ignorableNode(iterator.node)) {
        iterator.node = iterator.node.firstChild;
        iterator.depth++;
        return iterator;
        }
      while (iterator.node) {
       if (iterator.node.nextSibling) {
          iterator.node = iterator.node.nextSibling;
          return iterator;
          }
        iterator.node = iterator.node.parentNode;
        iterator.depth--;
        }
      return iterator;
      }


  /** All public functions **/

  return {
  
    queueAJAXRequest: function(token, method, url, parameters, callbacks) {
      RSF.log("queueAJAXRequest: token " + token);
      var callbacks1 = wrapCallbacks(callbacks, restartWrapper);
      var callbacks2 = wrapCallbacks(callbacks1, primaryRestorationWrapper());
      if (requestactive) {
        RSF.log("Request is active, queuing for token " + token);
        queuemap[token] = packAJAXRequest(method, url, parameters, callbacks2);
      }
      else {
        requestactive = true;
        myRSF.issueAJAXRequest(method, url, parameters, callbacks2);
      }
        
      function restartWrapper(callback) {
        return function() {
          requestactive = false;
          RSF.log("Restart callback wrapper begin");
          callback.apply(null, arguments);
          RSF.log("Callback concluded, beginning restart search");
          for (var i in queuemap) {
            RSF.log("Examining for token " + i);
            if (requestactive) return;
            var queued = queuemap[i];
            delete queuemap[i];
            RSF.queueAJAXRequest(token, queued.method, queued.url, queued.parameters, 
              queued.callback);
            }
          };
        }
      },
    
    issueAJAXRequest: function(method, url, parameters, callback) {
      method = method.toUpperCase(); // force method to uppercase for comparison
      var is_http_request = url.indexOf("http") === 0;
      var readyCallback = function() {
        if (http_request.readyState == 1) {
          //loading
          callback.loading();
        } else if (http_request.readyState == 4) {
          if (http_request.status == 200 || !is_http_request) {
            RSF.log("AJAX request success status: " + http_request.status);
            callback.success(http_request);
            RSF.log("AJAX callback concluded");
          } 
          else {
            RSF.log("AJAX request error status: " + http_request.status);
          }
        }
      }
      var http_request = false;
      if (window.XMLHttpRequest) { // Mozilla, Safari,...
        http_request = new XMLHttpRequest();
        if (method == "POST" && http_request.overrideMimeType) {
           // set type accordingly to anticipated content type
            //http_request.overrideMimeType('text/xml');
          http_request.overrideMimeType('text/xml');
          }
        } 
        else if (window.ActiveXObject) { // IE
          try {
            http_request = new ActiveXObject("Msxml2.XMLHTTP");
            } 
          catch (e) {
            try {
               http_request = new ActiveXObject("Microsoft.XMLHTTP");
            } catch (e) {}
          }
        }
         if (!http_request) {
           RSF.log('Cannot create XMLHTTP instance');
           return false;
         }
         
         http_request.onreadystatechange = readyCallback;
         if (method == "GET") {
            url = url + "?" + parameters;
         }
         http_request.open(method, url, true);
         if (method == "GET") {
            http_request.send(null);
         } else { // assume POST
            http_request.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
//            http_request.setRequestHeader("Content-length", parameters.length);
//            http_request.setRequestHeader("Connection", "close");
            http_request.send(parameters);
         }
         //delete(http_request); // NOTE: clearing this causes problems for IE7 and lower
         return true; // true if sent to the server
      },
  

    /** 
     * Sends a UVB AJAX request
     * sourceFields is a list of the JS form elements which you want to send in this request,
     * AJAXURL is the url to send to (this must be the UVB producer url),
     * bindings is a list of strings which indicate the bindings to return
     * callback is the function to be executed after loading the information
     * callbackLoading is the function to be executed during loading the information
     */
    getAJAXUpdater: function (sourceFields, AJAXURL, bindings, callbackLoaded,callbackLoading) {
      // this AJAXcallback will maintain 'loading' and 'success' functions
      // (para entender como funfa, procurar por 'literais de objeto')
      //alert("bindings: " + bindings);
      var AJAXcallback = {
        // this success function will be executed after the AJAX loading
        success: function(response) {
          RSF.log("Response success: " + response + " " + response.responseText);
          var UVB = RSF.accumulateUVBResponse(response.responseXML);
          RSF.log("Accumulated " + UVB);
          callbackLoaded(UVB);
        },
        // this loading function was made by ReadInWeb
        loading: function(){
      	  RSF.log("Loading AJAX request");
      	  callbackLoading();
        }
      };
      return function() {
        var body = RSF.getUVBSubmissionBody(sourceFields, bindings);
        RSF.log("Firing AJAX request " + body);
        myRSF.queueAJAXRequest(bindings[0], "POST", AJAXURL, body, AJAXcallback);
      }
    }
  }; // end return internal "Object"
}(); // end namespace RSF