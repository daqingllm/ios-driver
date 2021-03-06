/*
 * Copyright 2012-2013 eBay Software Foundation and ios-driver committers
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.uiautomation.ios.server.command.uiautomation;

import org.json.JSONException;
import org.json.JSONObject;
import org.uiautomation.ios.communication.WebDriverLikeRequest;
import org.uiautomation.ios.server.IOSServerManager;
import org.uiautomation.ios.server.command.UIAScriptHandler;

public class GetAttributeNHandler extends UIAScriptHandler {

  private static final String template =
      "var parent = UIAutomation.cache.get(:reference);" +
      "var myStringResult = parent:attribute ;" +
      "UIAutomation.createJSONResponse(':sessionId',0,myStringResult)";


  private static final String logElementTree = "var root = UIAutomation.cache.get(':reference');"
                                               + "var result = root.tree(false);"
                                               + "var str = JSON.stringify(result.tree);"
                                               + "UIAutomation.createJSONResponse(':sessionId',0,str);";

  public GetAttributeNHandler(IOSServerManager driver, WebDriverLikeRequest request) {
    super(driver, request);

    String attributeMethod = "." + request.getVariableValue(":name") + "()";
    String reference = request.getVariableValue(":reference");
    String js = null;

    if (".tree()".equals(attributeMethod)) {
      js = logElementTree.replace(":sessionId", request.getSession())
          .replace(":reference", reference);
    } else {
      js = template
          .replace(":sessionId", request.getSession())
          .replace(":attribute", attributeMethod)
          .replace(":reference", reference);
    }
    setJS(js);
  }

  @Override
  public JSONObject configurationDescription() throws JSONException {
    return noConfigDefined();
  }

}
