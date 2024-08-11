
import Alpine from "alpinejs"
import "./theme.scss" // parcel will compile this
import {el, els, debounce} from "./reusable_makin"

//material design github.com/material-components/material-web/blob/main/docs/quick-start.md
import '@material/web/all.js';
import {styles as typescaleStyles} from '@material/web/typography/md-typescale-styles.js';
document.adoptedStyleSheets.push(typescaleStyles.styleSheet);


window.Alpine = Alpine
