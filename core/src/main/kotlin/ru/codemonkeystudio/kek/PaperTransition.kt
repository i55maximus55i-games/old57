package ru.codemonkeystudio.kek

import de.eskalon.commons.screen.transition.impl.GLTransitionsShaderTransition

val paperTransitionShader = "// author: Hewlett-Packard\n" +
    "// license: BSD 3 Clause\n" +
    "// Adapted by Sergey Kosarevsky from:\n" +
    "// http://rectalogic.github.io/webvfx/examples_2transition-shader-pagecurl_8html-example.html\n" +
    "\n" +
    "/*\n" +
    "Copyright (c) 2010 Hewlett-Packard Development Company, L.P. All rights reserved.\n" +
    "\n" +
    "Redistribution and use in source and binary forms, with or without\n" +
    "modification, are permitted provided that the following conditions are\n" +
    "met:\n" +
    "\n" +
    "   * Redistributions of source code must retain the above copyright\n" +
    "     notice, this list of conditions and the following disclaimer.\n" +
    "   * Redistributions in binary form must reproduce the above\n" +
    "     copyright notice, this list of conditions and the following disclaimer\n" +
    "     in the documentation and/or other materials provided with the\n" +
    "     distribution.\n" +
    "   * Neither the name of Hewlett-Packard nor the names of its\n" +
    "     contributors may be used to endorse or promote products derived from\n" +
    "     this software without specific prior written permission.\n" +
    "\n" +
    "THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS\n" +
    "\"AS IS\" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT\n" +
    "LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR\n" +
    "A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT\n" +
    "OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,\n" +
    "SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT\n" +
    "LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,\n" +
    "DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY\n" +
    "THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT\n" +
    "(INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE\n" +
    "OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.\n" +
    "in vec2 texCoord;\n" +
    "*/\n" +
    "\n" +
    "const float MIN_AMOUNT = -0.16;\n" +
    "const float MAX_AMOUNT = 1.5;\n" +
    "float amount = progress * (MAX_AMOUNT - MIN_AMOUNT) + MIN_AMOUNT;\n" +
    "\n" +
    "const float PI = 3.141592653589793;\n" +
    "\n" +
    "const float scale = 512.0;\n" +
    "const float sharpness = 3.0;\n" +
    "\n" +
    "float cylinderCenter = amount;\n" +
    "// 360 degrees * amount\n" +
    "float cylinderAngle = 2.0 * PI * amount;\n" +
    "\n" +
    "const float cylinderRadius = 1.0 / PI / 2.0;\n" +
    "\n" +
    "vec3 hitPoint(float hitAngle, float yc, vec3 point, mat3 rrotation)\n" +
    "{\n" +
    "        float hitPoint = hitAngle / (2.0 * PI);\n" +
    "        point.y = hitPoint;\n" +
    "        return rrotation * point;\n" +
    "}\n" +
    "\n" +
    "vec4 antiAlias(vec4 color1, vec4 color2, float distanc)\n" +
    "{\n" +
    "        distanc *= scale;\n" +
    "        if (distanc < 0.0) return color2;\n" +
    "        if (distanc > 2.0) return color1;\n" +
    "        float dd = pow(1.0 - distanc / 2.0, sharpness);\n" +
    "        return ((color2 - color1) * dd) + color1;\n" +
    "}\n" +
    "\n" +
    "float distanceToEdge(vec3 point)\n" +
    "{\n" +
    "        float dx = abs(point.x > 0.5 ? 1.0 - point.x : point.x);\n" +
    "        float dy = abs(point.y > 0.5 ? 1.0 - point.y : point.y);\n" +
    "        if (point.x < 0.0) dx = -point.x;\n" +
    "        if (point.x > 1.0) dx = point.x - 1.0;\n" +
    "        if (point.y < 0.0) dy = -point.y;\n" +
    "        if (point.y > 1.0) dy = point.y - 1.0;\n" +
    "        if ((point.x < 0.0 || point.x > 1.0) && (point.y < 0.0 || point.y > 1.0)) return sqrt(dx * dx + dy * dy);\n" +
    "        return min(dx, dy);\n" +
    "}\n" +
    "\n" +
    "vec4 seeThrough(float yc, vec2 p, mat3 rotation, mat3 rrotation)\n" +
    "{\n" +
    "        float hitAngle = PI - (acos(yc / cylinderRadius) - cylinderAngle);\n" +
    "        vec3 point = hitPoint(hitAngle, yc, rotation * vec3(p, 1.0), rrotation);\n" +
    "        if (yc <= 0.0 && (point.x < 0.0 || point.y < 0.0 || point.x > 1.0 || point.y > 1.0))\n" +
    "        {\n" +
    "            return getToColor(p);\n" +
    "        }\n" +
    "\n" +
    "        if (yc > 0.0) return getFromColor(p);\n" +
    "\n" +
    "        vec4 color = getFromColor(point.xy);\n" +
    "        vec4 tcolor = vec4(0.0);\n" +
    "\n" +
    "        return antiAlias(color, tcolor, distanceToEdge(point));\n" +
    "}\n" +
    "\n" +
    "vec4 seeThroughWithShadow(float yc, vec2 p, vec3 point, mat3 rotation, mat3 rrotation)\n" +
    "{\n" +
    "        float shadow = distanceToEdge(point) * 30.0;\n" +
    "        shadow = (1.0 - shadow) / 3.0;\n" +
    "\n" +
    "        if (shadow < 0.0) shadow = 0.0; else shadow *= amount;\n" +
    "\n" +
    "        vec4 shadowColor = seeThrough(yc, p, rotation, rrotation);\n" +
    "        shadowColor.r -= shadow;\n" +
    "        shadowColor.g -= shadow;\n" +
    "        shadowColor.b -= shadow;\n" +
    "\n" +
    "        return shadowColor;\n" +
    "}\n" +
    "\n" +
    "vec4 backside(float yc, vec3 point)\n" +
    "{\n" +
    "        vec4 color = getFromColor(point.xy);\n" +
    "        float gray = (color.r + color.b + color.g) / 15.0;\n" +
    "        gray += (8.0 / 10.0) * (pow(1.0 - abs(yc / cylinderRadius), 2.0 / 10.0) / 2.0 + (5.0 / 10.0));\n" +
    "        color.rgb = vec3(gray);\n" +
    "        return color;\n" +
    "}\n" +
    "\n" +
    "vec4 behindSurface(vec2 p, float yc, vec3 point, mat3 rrotation)\n" +
    "{\n" +
    "        float shado = (1.0 - ((-cylinderRadius - yc) / amount * 7.0)) / 6.0;\n" +
    "        shado *= 1.0 - abs(point.x - 0.5);\n" +
    "\n" +
    "        yc = (-cylinderRadius - cylinderRadius - yc);\n" +
    "\n" +
    "        float hitAngle = (acos(yc / cylinderRadius) + cylinderAngle) - PI;\n" +
    "        point = hitPoint(hitAngle, yc, point, rrotation);\n" +
    "\n" +
    "        if (yc < 0.0 && point.x >= 0.0 && point.y >= 0.0 && point.x <= 1.0 && point.y <= 1.0 && (hitAngle < PI || amount > 0.5))\n" +
    "        {\n" +
    "                shado = 1.0 - (sqrt(pow(point.x - 0.5, 2.0) + pow(point.y - 0.5, 2.0)) / (71.0 / 100.0));\n" +
    "                shado *= pow(-yc / cylinderRadius, 3.0);\n" +
    "                shado *= 0.5;\n" +
    "        }\n" +
    "        else\n" +
    "        {\n" +
    "                shado = 0.0;\n" +
    "        }\n" +
    "        return vec4(getToColor(p).rgb - shado, 1.0);\n" +
    "}\n" +
    "\n" +
    "vec4 transition(vec2 p) {\n" +
    "\n" +
    "  const float angle = 100.0 * PI / 180.0;\n" +
    "        float c = cos(-angle);\n" +
    "        float s = sin(-angle);\n" +
    "\n" +
    "        mat3 rotation = mat3( c, s, 0,\n" +
    "                                                                -s, c, 0,\n" +
    "                                                                -0.801, 0.8900, 1\n" +
    "                                                                );\n" +
    "        c = cos(angle);\n" +
    "        s = sin(angle);\n" +
    "\n" +
    "        mat3 rrotation = mat3(\tc, s, 0,\n" +
    "                                                                        -s, c, 0,\n" +
    "                                                                        0.98500, 0.985, 1\n" +
    "                                                                );\n" +
    "\n" +
    "        vec3 point = rotation * vec3(p, 1.0);\n" +
    "\n" +
    "        float yc = point.y - cylinderCenter;\n" +
    "\n" +
    "        if (yc < -cylinderRadius)\n" +
    "        {\n" +
    "                // Behind surface\n" +
    "                return behindSurface(p,yc, point, rrotation);\n" +
    "        }\n" +
    "\n" +
    "        if (yc > cylinderRadius)\n" +
    "        {\n" +
    "                // Flat surface\n" +
    "                return getFromColor(p);\n" +
    "        }\n" +
    "\n" +
    "        float hitAngle = (acos(yc / cylinderRadius) + cylinderAngle) - PI;\n" +
    "\n" +
    "        float hitAngleMod = mod(hitAngle, 2.0 * PI);\n" +
    "        if ((hitAngleMod > PI && amount < 0.5) || (hitAngleMod > PI/2.0 && amount < 0.0))\n" +
    "        {\n" +
    "                return seeThrough(yc, p, rotation, rrotation);\n" +
    "        }\n" +
    "\n" +
    "        point = hitPoint(hitAngle, yc, point, rrotation);\n" +
    "\n" +
    "        if (point.x < 0.0 || point.y < 0.0 || point.x > 1.0 || point.y > 1.0)\n" +
    "        {\n" +
    "                return seeThroughWithShadow(yc, p, point, rotation, rrotation);\n" +
    "        }\n" +
    "\n" +
    "        vec4 color = backside(yc, point);\n" +
    "\n" +
    "        vec4 otherColor;\n" +
    "        if (yc < 0.0)\n" +
    "        {\n" +
    "                float shado = 1.0 - (sqrt(pow(point.x - 0.5, 2.0) + pow(point.y - 0.5, 2.0)) / 0.71);\n" +
    "                shado *= pow(-yc / cylinderRadius, 3.0);\n" +
    "                shado *= 0.5;\n" +
    "                otherColor = vec4(0.0, 0.0, 0.0, shado);\n" +
    "        }\n" +
    "        else\n" +
    "        {\n" +
    "                otherColor = getFromColor(p);\n" +
    "        }\n" +
    "\n" +
    "        color = antiAlias(color, otherColor, cylinderRadius - abs(yc));\n" +
    "\n" +
    "        vec4 cl = seeThroughWithShadow(yc, p, point, rotation, rrotation);\n" +
    "        float dist = distanceToEdge(point);\n" +
    "\n" +
    "        return antiAlias(color, cl, dist);\n" +
    "}\n"


val paperTransition___0_5 = GLTransitionsShaderTransition(paperTransitionShader, 0.5f)
val paperTransition___1_0 = GLTransitionsShaderTransition(paperTransitionShader, 0.5f)
val paperTransition___1_5 = GLTransitionsShaderTransition(paperTransitionShader, 0.5f)
val paperTransition___2_0 = GLTransitionsShaderTransition(paperTransitionShader, 0.5f)
val paperTransition___2_5 = GLTransitionsShaderTransition(paperTransitionShader, 0.5f)
