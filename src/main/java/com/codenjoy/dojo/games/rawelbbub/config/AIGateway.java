package com.codenjoy.dojo.games.rawelbbub.config;

/*-
 * #%L
 * Codenjoy - it's a dojo-like platform from developers to developers.
 * %%
 * Copyright (C) 2012 - 2024 Codenjoy
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public
 * License along with this program.  If not, see
 * <http://www.gnu.org/licenses/gpl-3.0.html>.
 * #L%
 */

import feign.HeaderMap;
import feign.Param;
import feign.RequestLine;

public interface AIGateway {
    @RequestLine("POST /openai/deployments/{deploymentName}/chat/completions?api-version={apiVersion}")
    Object getNextMove(@Param("deploymentName") String deploymentName, @Param("apiVersion") String apiVersion, @HeaderMap Object headers, Object body);

    @RequestLine("POST /v1/chat/completions")
    Object getNextMoveLocal(@HeaderMap Object headers, Object body);
}
