DESCRIPTION = "Node-RED"
HOMEPAGE = "http://nodered.org"
LICENSE = "Apache-2.0"
LIC_FILES_CHKSUM = "file://LICENSE;md5=d6f37569f5013072e9490d2194d10ae6"

inherit npm

PR = "r0"

SRC_URI = "\
    npm://registry.npmjs.org;name=${PN};version=${PV} \
    file://node-red.service \
"

NPMPN = "${PN}"
NPM_LOCKDOWN := "${THISDIR}/${PN}/package-lock.json"
NPM_SHRINKWRAP := "${THISDIR}/${PN}/npm-shrinkwrap.json"

do_install_append() {
    # Service
    install -d ${D}${systemd_unitdir}/system/
    install -m 0644 ${WORKDIR}/${PN}.service ${D}${systemd_unitdir}/system/
    
    # Remove hardware specific files
    rm ${D}${bindir}/${PN}-pi
    rm -rf ${D}${NPM_INSTALLDIR}/bin
    rm -rf ${D}${NPM_INSTALLDIR}/node_modules/@${PN}/nodes/core/hardware
}

inherit systemd

SYSTEMD_AUTO_ENABLE = "enable"
SYSTEMD_SERVICE_${PN} = "${PN}.service"

FILES_${PN} += "\
    ${bindir} \
    ${systemd_unitdir} \
"
