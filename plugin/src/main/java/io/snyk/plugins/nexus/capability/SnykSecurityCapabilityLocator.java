package io.snyk.plugins.nexus.capability;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sonatype.nexus.capability.CapabilityReference;
import org.sonatype.nexus.capability.CapabilityRegistry;

@Named
@Singleton
public class SnykSecurityCapabilityLocator {
  private static final Logger LOG = LoggerFactory.getLogger(SnykSecurityCapabilityLocator.class);

  private final CapabilityRegistry capabilityRegistry;

  @Inject
  public SnykSecurityCapabilityLocator(final CapabilityRegistry capabilityRegistry) {
    this.capabilityRegistry = capabilityRegistry;
  }

  public SnykSecurityCapabilityConfiguration getSnykSecurityCapabilityConfiguration() {
    CapabilityReference reference = capabilityRegistry.getAll().stream()
                                                      .filter(e -> e.capability().getClass().isInstance(SnykSecurityCapability.class))
                                                      .findFirst().orElse(null);
    if (reference == null) {
      LOG.debug("Snyk Security Configuration capability not created.");
      return null;
    }

    SnykSecurityCapability snykSecurityCapability = reference.capabilityAs(SnykSecurityCapability.class);
    return snykSecurityCapability.getConfig();
  }
}
