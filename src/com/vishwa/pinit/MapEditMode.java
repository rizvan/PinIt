/*
 * Copyright 2013 Vishwa Patel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License in the 'assets' directory of this 
 * application or at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vishwa.pinit;

public enum MapEditMode {
    DEFAULT_MODE(0),
    CREATE_NOTE(1),
    EDIT_NOTE(2);

    private int code;

    private MapEditMode(int code) {
        this.code = code; 
    }

    public int getCode() {
        return code;
    }

}
